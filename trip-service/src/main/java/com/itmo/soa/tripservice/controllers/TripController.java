package com.itmo.soa.tripservice.controllers;

import com.itmo.soa.tripservice.services.TripService;
import com.itmo.soa.tripservice.services.TripServiceImpl;
import lombok.RequiredArgsConstructor;
import model.dto.ErrorDTO;
import model.dto.TicketDTO;
import model.exception.RouteNotFoundException;
import model.exception.TicketNotUniqueException;
import model.response.TicketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = "application/json")
public class TripController {
    private final Logger logger = LoggerFactory.getLogger(TripController.class);
    @Autowired
    private final TripService tripService;

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello!", HttpStatus.OK);
    }

    @PostMapping("/tickets")
    ResponseEntity<TicketResponse> buyTicket(@RequestBody TicketDTO ticketDTO) {
        var result = tripService.buyTicket(ticketDTO);
        HttpStatus status = result.getError() == null ? HttpStatus.OK : HttpStatus.valueOf(result.getError().getCode());
        var res = new ResponseEntity<>(result, status);
        logger.info(res.toString());
        return res;
    }

    @GetMapping("/tickets/{passengerId}")
    ResponseEntity<List<TicketDTO>> getPassengersTickets(@PathVariable("passengerId") String id) {
        var res = new ResponseEntity<>(tripService.getPassengersTickets(id), HttpStatus.OK);
        logger.info(res.toString());
        return res;
    }

    @DeleteMapping("/tickets/{id}/{passengerId}")
    ResponseEntity<TicketResponse> deleteTicketById(@PathVariable("id") Long ticketId, @PathVariable("passengerId") String passengerId) {
        var result = tripService.deleteTicketById(ticketId, passengerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Deprecated
    @DeleteMapping("/tickets/{routeId}")
    ResponseEntity<List<TicketDTO>> deleteTicketByRouteId(@PathVariable("routeId") Long routeId) {
        return new ResponseEntity<>(tripService.deleteTicketsByRouteId(routeId), HttpStatus.OK);
    }
}
