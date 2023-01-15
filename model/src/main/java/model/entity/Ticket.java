package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1, sequenceName = "ticket_seq")
    @Column(name = "ticket_id", nullable = false)
    private long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route direction;

    @NonNull
    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    @Column(name = "price")
    private double price;
}
