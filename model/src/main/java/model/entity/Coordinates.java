package model.entity;

import lombok.*;

import javax.persistence.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "coordinate")
public class Coordinates implements Serializable {
//    @EmbeddedId
//    CoordinatesId coordinates_id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1, sequenceName = "coordinates_seq")
    @Column(name = "coordinates_id", nullable = false)
    long coordinates_id;

    @NonNull
    @Column(name = "x_coordinate")
    long x_coordinate;

    @NonNull
    @Column(name = "y_coordinate")
    double y_coordinate;

    public Coordinates(Long xCoordinate, Double yCoordinate) {
        this.x_coordinate = xCoordinate;
        this.y_coordinate = yCoordinate;
    }
}
