package model.response;

import lombok.*;
import model.dto.RouteDTO;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteSearchResponse implements Serializable {
    /**
     * Список с маршрутами, соответствующими критерию
     * */
    private List<RouteDTO> list;

    /**
     * Номер страницы
     * */
    private Integer pageNumber;
}
