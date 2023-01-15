package model.request;

import lombok.*;
import model.entity.Coordinates;
import model.entity.Location;
import model.entity.NamedLocation;
import model.entity.Route;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RouteCreateRequest implements Serializable {
    /**
     * Название маршрута
     * */
    @NotBlank
    private String name;

    /**
     * Координаты маршрута
     * */
    private Long idCoordinate;
    private Long x;

    private Double y;

    /**
     * Начальное местоположение
     * */
    private Long fromId;

    private Float fromX;

    private Long fromY;

    private Integer fromZ;

    /**
     * Конечное местоположение
     * */
    private Long toId;

    private Long toX;

    private Integer toY;

    private Long toZ;

    private String toName;

    /**
     * Расстояние между местоположениями в маршруте
     * */
    @Min(value = 2)
    private Long distance;

    public Route convertFrom() {
        return new Route(name, new Coordinates(x, y), new Location(fromX, fromY, fromZ),
                new NamedLocation(toX, toY, toZ, toName), distance);
    }

    public Route convertFromWithLocations(Location from, NamedLocation to) {
        return new Route(name, new Coordinates(x, y), from, to, distance);
    }

    public boolean validate() {
        boolean requiredFields = name != null && !name.isBlank() &&
                x != null && y != null &&
                fromX != null && fromY != null && fromZ != null
                && distance > 1;
        boolean toField = true;
        if (toName != null || toX != null || toY != null || toZ != null) {
            toField = toName != null && toX != null && toY != null && toZ != null && !toName.isBlank();
        }
        return requiredFields && toField;
    }
}
