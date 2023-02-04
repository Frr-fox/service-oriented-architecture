package com.itmo.soa.tripservice.services;

import model.dto.TicketDTO;
import model.response.TicketResponse;

import java.util.List;

public interface TripService {
    TicketResponse buyTicket(TicketDTO ticketDTO);

    List<TicketDTO> getPassengersTickets(String id);

    TicketResponse deleteTicketById(Long ticketId, String passengerId);

    List<TicketDTO> deleteTicketsByRouteId(Long routeId);
}
