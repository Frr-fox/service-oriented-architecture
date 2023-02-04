package model.entity;

import lombok.*;

import javax.persistence.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location implements Serializable {
//    @EmbeddedId
//    LocationId<Float, Long, Integer> location_id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1, sequenceName = "location_seq")
    @Column(name = "location_id", nullable = false)
    long location_id;

    @NonNull
    @Column(name = "x_coordinate")
    Float x_coordinate;

    @NonNull
    @Column(name = "y_coordinate")
    Long y_coordinate;

    @NonNull
    @Column(name = "z_coordinate")
    Integer z_coordinate;

    public Location(Float fromX, Long fromY, Integer fromZ) {
        this.x_coordinate = fromX;
        this.y_coordinate = fromY;
        this.z_coordinate = fromZ;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != Location.class) {
            return false;
        }
        Location location = (Location) object;
        return (this.getX_coordinate().equals(location.getX_coordinate()) &&
                this.getY_coordinate().equals(location.getY_coordinate()) &&
                this.getZ_coordinate().equals(location.getZ_coordinate())) ||
                (this.getLocation_id() == location.getLocation_id());
    }
}
