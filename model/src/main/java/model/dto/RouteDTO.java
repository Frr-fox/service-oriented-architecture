package model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RouteDTO implements Serializable {
    /**
    * Идентификатор маршрута
    * */
    @NonNull
    @Min(value = 1)
    private long id;

    /**
    * Название маршрута
    * */
    @NonNull
    @NotBlank
    private String name;

    /**
    * Координаты маршрута
    * */
    private Long idCoordinate;

    @NonNull
    private Long x;

    @NonNull
    private Double y;

    /**
     * Дата создания маршрута
     * */
    @NonNull
    private LocalDateTime creationDate;

    /**
     * Начальное местоположение
     * */
    private Long fromId;

    @NonNull
    private Float fromX;

    @NonNull
    private Long fromY;

    @NonNull
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
    @NonNull
    @Min(value = 2)
    private long distance;
}
