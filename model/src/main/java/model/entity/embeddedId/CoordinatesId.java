package model.entity.embeddedId;

import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoordinatesId implements Serializable {
    @NonNull
    @Column(name = "x_coordinate")
    long x_coordinate;

    @NonNull
    @Column(name = "y_coordinate")
    double y_coordinate;
}
