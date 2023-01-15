package model.converter;

import model.entity.Coordinates;
import model.entity.Location;
import model.entity.NamedLocation;
import model.entity.Route;
import model.dto.RouteDTO;

import javax.ejb.Stateless;

@Stateless
public class RouteDTOConverter {

    public Route convertTo(RouteDTO routeDTO) {
        Route newEntity = new Route();
        newEntity.setId(routeDTO.getId());
        newEntity.setName(routeDTO.getName());
        newEntity.setCoordinates(new Coordinates(routeDTO.getX(), routeDTO.getY()));
        newEntity.setCreationDate(routeDTO.getCreationDate());
        newEntity.setFrom(new Location(routeDTO.getFromX(), routeDTO.getFromY(), routeDTO.getFromZ()));
        newEntity.setTo(new NamedLocation(routeDTO.getToX(), routeDTO.getToY(), routeDTO.getToZ(), routeDTO.getToName()));
        newEntity.setDistance(routeDTO.getDistance());
        return newEntity;
    }

    public RouteDTO convertFrom(Route route) {
        return RouteDTO.builder()
                .id(route.getId())
                .name(route.getName())
                .x(route.getCoordinates().getX_coordinate())
                .y(route.getCoordinates().getY_coordinate())
                .creationDate(route.getCreationDate())
                .fromX(route.getFrom().getX_coordinate())
                .fromY(route.getFrom().getY_coordinate())
                .fromZ(route.getFrom().getZ_coordinate())
                .toX(route.getTo().getX_coordinate())
                .toY(route.getTo().getY_coordinate())
                .toZ(route.getTo().getZ_coordinate())
                .toName(route.getTo().getName())
                .distance(route.getDistance())
                .build();
    }
}
