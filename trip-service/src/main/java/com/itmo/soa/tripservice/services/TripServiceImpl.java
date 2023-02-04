package com.itmo.soa.tripservice.services;

import com.itmo.soa.tripservice.converters.TicketDTOConverter;
import com.itmo.soa.tripservice.repositories.PassengerRepository;
import model.converter.RouteDTOConverter;
import model.dto.ErrorDTO;
import model.dto.RouteDTO;
import model.entity.Passenger;
import model.exception.PassengerIdNotMatchException;
import model.exception.RouteNotFoundException;
import model.exception.TicketNotFoundException;
import com.itmo.soa.tripservice.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import model.dto.TicketDTO;
import model.entity.Route;
import model.entity.Ticket;
import model.exception.TicketNotUniqueException;
import model.response.TicketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TicketDTOConverter ticketDTOConverter;
    @Autowired
    private RouteDTOConverter routeDTOConverter;
    @Autowired
    private WebClientProvider webClientProvider;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public TicketResponse buyTicket(TicketDTO ticketDTO) {
        try {
            logger.info(ticketDTO.toString());
            RouteDTO routeDTO = new RouteDTO();
            try {
                routeDTO = webClientProvider.getWebClient()
                        .get()
                        .uri(String.join("", "/", ticketDTO.getRouteId().toString()))
                        .retrieve()
                        .bodyToMono(RouteDTO.class)
                        .block();
            } catch (WebClientResponseException e) {
                logger.warn(e.getMessage());
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new RouteNotFoundException(ticketDTO.getRouteId().toString());
                }
            }
            logger.info(routeDTO.toString());
            if (routeDTO.getError() != null) {
                throw new RouteNotFoundException(ticketDTO.getRouteId().toString());
            }
            Route route = routeDTOConverter.convertTo(routeDTO);
            Ticket ticket = ticketDTOConverter.convertTo(ticketDTO, route);
            var res = ticketRepository.findAllByDirectionAndDepartureDateAndPlace(
                    route, ticket.getDepartureDate(), ticket.getPlace());
            logger.info(res.toString());
            if (!res.isEmpty()) {
                throw new TicketNotUniqueException();
            }
            logger.info(ticket.toString());
            ticket.setPrice(calculatePrice(route));
            logger.info("Price: " + ticket.getPrice());
            Optional<Passenger> possiblePassenger = passengerRepository.findById(ticket.getPassenger().getId());
            if (possiblePassenger.isPresent()) {
                if (checkPassengerData(possiblePassenger.get(), ticketDTO)) {
                    ticket.setPassenger(possiblePassenger.get());
                } else {
                    throw new PassengerIdNotMatchException();
                }
            }
            ticketRepository.save(ticket);
            return TicketResponse.builder()
                    .ticket(ticketDTOConverter.convertFrom(ticket))
                    .build();
        } catch (TicketNotUniqueException e) {
            return TicketResponse.builder()
                    .error(ErrorDTO.builder()
                            .code(400)
                            .message(e.getMessage())
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        } catch (RouteNotFoundException e) {
            return TicketResponse.builder()
                    .error(ErrorDTO.builder()
                            .code(404)
                            .message(e.getMessage())
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        } catch (PassengerIdNotMatchException e) {
            return TicketResponse.builder()
                    .error(ErrorDTO.builder()
                            .code(403)
                            .message(e.getMessage())
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @Override
    public List<TicketDTO> getPassengersTickets(String id) {
//        Optional<Passenger> possiblePassenger = passengerRepository.findById(id);
//        if (possiblePassenger.isEmpty()) {
//            throw new PassengerNotFoundException(id);
//        }
        logger.info("Get passenger's tickets with ID = " + id);
        List<Ticket> tickets = ticketRepository.findAllByPassengerId(id);
        logger.info("Tickets count: " + tickets.stream().count());
        List<TicketDTO> result = new ArrayList<>();
        for (Ticket ticket: tickets) {
            result.add(ticketDTOConverter.convertFrom(ticket));
        }
        logger.info(result.toString());
        return result;
    }

    @Override
    public TicketResponse deleteTicketById(Long ticketId, String passengerId) {
        try {
            Optional<Ticket> possibleTicket = ticketRepository.findById(ticketId);
            if (possibleTicket.isEmpty()) {
                throw new TicketNotFoundException(ticketId.toString());
            }
            Ticket ticket = possibleTicket.get();
            if (Objects.equals(ticket.getPassenger().getId(), passengerId)) {
                ticketRepository.deleteById(ticketId);
            } else {
                throw new PassengerIdNotMatchException();
            }
            return TicketResponse.builder()
                    .ticket(ticketDTOConverter.convertFrom(ticket))
                    .build();
        } catch (TicketNotFoundException e) {
            return TicketResponse.builder()
                    .error(ErrorDTO.builder()
                            .code(404)
                            .message(e.getMessage())
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        } catch (PassengerIdNotMatchException e) {
            return TicketResponse.builder()
                    .error(ErrorDTO.builder()
                            .code(403)
                            .message(e.getMessage())
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @Override
    public List<TicketDTO> deleteTicketsByRouteId(Long routeId) {
        List<Ticket> tickets = ticketRepository.findAllByDirection_Id(routeId);
        ticketRepository.deleteAllByDirection_Id(routeId);
        List<TicketDTO> ticketsDTO = new ArrayList<>();
        for (Ticket ticket: tickets) {
            ticketsDTO.add(ticketDTOConverter.convertFrom(ticket));
        }
        return ticketsDTO;
    }

    private Double calculatePrice(Route route) {
        double multiplier = 12;
        return Math.sqrt(Math.pow(route.getCoordinates().getX_coordinate(), 2) +
                Math.pow(route.getCoordinates().getY_coordinate(), 2)) * route.getDistance() * multiplier;
    }

    private boolean checkPassengerData(Passenger passenger, TicketDTO ticketDTO) {
        return passenger.getName().equals(ticketDTO.getName()) &&
                passenger.getSurname().equals(ticketDTO.getSurname()) &&
                passenger.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(ticketDTO.getBirthDate());
    }
}
