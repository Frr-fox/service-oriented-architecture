package model.entity;

import lombok.*;

import javax.persistence.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "route")
@ToString
public class Route implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1, sequenceName = "route_seq")
    @Column(name = "route_id", nullable = false)
    long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(name = "name")
    String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "coordinates_id")
    Coordinates coordinates; //Поле не может быть null

    @Column(name = "creation_date")
    LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "from_location")
    Location from; //Поле не может быть null

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "to_location")
    NamedLocation to; //Поле может быть null

    @Column(name = "distance")
    long distance; //Значение поля должно быть больше 1

    public Route(String name, Coordinates coordinates, Location from, NamedLocation to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(String name, Coordinates coordinates, LocalDateTime creationDate, Location from, NamedLocation to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}
