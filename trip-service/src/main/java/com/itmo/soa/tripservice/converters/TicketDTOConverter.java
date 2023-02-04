package com.itmo.soa.tripservice.converters;

import model.dto.TicketDTO;
import model.entity.Passenger;
import model.entity.Route;
import model.entity.Ticket;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TicketDTOConverter {
    String dateFormat = "yyyy-MM-dd";

    public Ticket convertTo(TicketDTO ticketDTO, Route route) {
        Ticket newEntity = new Ticket();
        newEntity.setId(ticketDTO.getId());
        newEntity.setPassenger(new Passenger(ticketDTO.getPassengerId(), ticketDTO.getName(),
                ticketDTO.getSurname(), LocalDate.parse(ticketDTO.getBirthDate(), DateTimeFormatter.ofPattern(dateFormat)).atStartOfDay()));
        newEntity.setDirection(route);
        newEntity.setDepartureDate(LocalDate.parse(ticketDTO.getDepartureDate(), DateTimeFormatter.ofPattern(dateFormat)).atStartOfDay());
        newEntity.setPlace(ticketDTO.getPlace());
        newEntity.setPrice(ticketDTO.getPrice());
        return newEntity;
    }

    public TicketDTO convertFrom(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .routeId(ticket.getDirection().getId())
                .passengerId(ticket.getPassenger().getId())
                .name(ticket.getPassenger().getName())
                .surname(ticket.getPassenger().getSurname())
                .birthDate(ticket.getPassenger().getBirthDate().format(DateTimeFormatter.ofPattern(dateFormat)))
                .departureDate(ticket.getDepartureDate().format(DateTimeFormatter.ofPattern(dateFormat)))
                .place(ticket.getPlace())
                .price(ticket.getPrice())
                .build();
    }
}
