package model.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket", uniqueConstraints = { @UniqueConstraint(columnNames = { "route_id", "departure_date", "place" }) })
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1, sequenceName = "ticket_seq")
    @Column(name = "ticket_id", nullable = false)
    private long id;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Route direction;

    @NonNull
    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "place")
    private String place;

    @Column(name = "price")
    private double price;
}
