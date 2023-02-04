package com.itmo.soa.navigatorservice.service;

import com.itmo.soa.navigatorservice.utils.SortCondition;
import model.dto.*;
import model.request.RouteCreateBetweenLocationsRequest;
import model.request.RouteCreateRequest;
import model.response.RouteSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Stateless
public class NavigatorServiceImpl implements NavigatorService {
    private final Logger logger = LoggerFactory.getLogger(NavigatorServiceImpl.class);
    private final String baseURL = "https://localhost:31471/api/v1/routes";

    @Inject
    private ClientProvider clientProvider;

    @Override
    public List<RouteDTO> getAllRoutesBetweenLocationsByIdSorted(Long idLocationFrom,
                                                                 Long idLocationTo,
                                                                 String sortCondition,
                                                                 Integer page,
                                                                 Integer limit) {
        //todo: вызов rest api другого сервиса
        RouteSearchResponse response = clientProvider.getClient().target(baseURL + "?page=" + page + "&limit=" + limit)
                .request(MediaType.APPLICATION_JSON)
                .get(RouteSearchResponse.class);
        logger.info(response.toString());
        SortCondition condition = SortCondition.convert(sortCondition);
        List<RouteDTO> list = response.getList().stream()
                .filter(routeDTO -> Objects.equals(routeDTO.getFromId(), idLocationFrom))
                .filter(routeDTO -> Objects.equals(routeDTO.getToId(), idLocationTo))
                .sorted((a, b) -> condition.getComparator().compare(a, b))
                .collect(Collectors.toList());
        logger.info(list.toString());
        return list;
    }

    @Override
    public boolean createRouteBetweenLocations(Long idLocationFrom,
                                            Long idLocationTo,
                                            Long distance,
                                            RouteCreateBetweenLocationsRequest request) {
        //todo: вызов rest api другого сервиса
        logger.info(request.toString());
        Response response = clientProvider.getClient().target(baseURL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(RouteCreateRequest.builder()
                                .name(request.getName())
                                        .x(request.getX())
                                        .y(request.getY())
                                        .fromId(idLocationFrom)
                                        .toId(idLocationTo)
                                        .distance(distance)
                                .build(),
                        MediaType.APPLICATION_JSON));
        return response.getStatus() == 200;
    }
}
