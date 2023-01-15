package model.entity;

import lombok.*;

import javax.persistence.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "named_location")
public class NamedLocation implements Serializable {
//    @EmbeddedId
//    LocationId<Long, Integer, Long> location_id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1, sequenceName = "named_location_seq")
    @Column(name = "location_id", nullable = false)
    long location_id;

    @NonNull
    @Column(name = "x_coordinate")
    Long x_coordinate;

    @NonNull
    @Column(name = "y_coordinate")
    Integer y_coordinate;

    @NonNull
    @Column(name = "z_coordinate")
    Long z_coordinate;

    @NotBlank
    @NonNull
    @Column(name = "name")
    String name; //Строка не может быть пустой, Поле не может быть null

    public NamedLocation(Long toX, Integer toY, Long toZ, String toName) {
        this.x_coordinate = toX;
        this.y_coordinate = toY;
        this.z_coordinate = toZ;
        this.name = toName;
    }
}
