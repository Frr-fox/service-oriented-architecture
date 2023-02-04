package model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicketDTO implements Serializable {
    private long id;

    private Long routeId;

    private String passengerId;

    private String name;

    private String surname;

    private String birthDate;

    private String departureDate;

    private String place;

    private double price;
}
