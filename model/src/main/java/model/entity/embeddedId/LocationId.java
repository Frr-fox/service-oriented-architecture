package model.entity.embeddedId;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Embeddable;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationId<T, V, M> implements Serializable {
    @NonNull
    @Column(name = "x_coordinate")
    T x_coordinate;

    @NonNull
    @Column(name = "y_coordinate")
    V y_coordinate;

    @NonNull
    @Column(name = "z_coordinate")
    M z_coordinate;
}
