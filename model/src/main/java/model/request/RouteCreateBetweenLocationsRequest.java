package model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import model.entity.Coordinates;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
