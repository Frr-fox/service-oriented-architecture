package model.request;

import lombok.*;
import model.entity.Coordinates;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteCreateBetweenLocationsRequest implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NonNull
    private Long x;

    @NonNull
    private Double y;

    public boolean validate() {
        return name != null && !name.isBlank() && x != null && y != null;
    }
}
