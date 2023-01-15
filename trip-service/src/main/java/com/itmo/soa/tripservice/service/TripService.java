package com.itmo.soa.tripservice.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tickets/")
public interface TripService {
    @POST
    @Path("{routeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response buyTicket(@PathParam("routeId") Long routeId);

    @GET
    @Path("{passengerId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPassengersTickets(@PathParam("passengerId") String id);

    @DELETE
    @Path("{id}")
    Response deleteTicketById(@PathParam("id") Long ticketId);
}
