package model.request;

import lombok.*;
import model.entity.Coordinates;
import model.entity.Location;
import model.entity.NamedLocation;
import model.entity.Route;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RouteUpdateRequest implements Serializable {
    /**
     * Название маршрута
     * */
    private String name;

    /**
     * Координаты маршрута
     * */
    private Long x;

    private Double y;

    /**
     * Дата создания маршрута
     * */
    private LocalDateTime creationDate;

    /**
     * Начальное местоположение
     * */
    private Float fromX;

    private Long fromY;

    private Integer fromZ;

    /**
     * Конечное местоположение
     * */
    private Long toX;

    private Integer toY;

    private Long toZ;

    private String toName;

    /**
     * Расстояние между местоположениями в маршруте
     * */
    @Min(value = 2)
    private long distance;

    public Route convertFrom() {
        return new Route(name, new Coordinates(x, y), creationDate, new Location(fromX, fromY, fromZ),
                new NamedLocation(toX, toY, toZ, toName), distance);
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
