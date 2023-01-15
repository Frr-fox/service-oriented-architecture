package com.itmo.soa.routeservice.resource;

import com.itmo.soa.routeservice.service.AdditionalRouteService;
import model.dto.ErrorDTO;
import model.dto.PageDTO;
import model.dto.RouteDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/routes")
public class AdditionalRouteServiceResource {
    @Inject
    private AdditionalRouteService additionalRouteService;

    @GET
    @Path("/to/min")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRouteWithMinTo() {
        RouteDTO routeDTO = additionalRouteService.getRouteWithMinTo();
        if (routeDTO != null) {
            return Response.ok()
                    .entity(routeDTO)
                    .build();
        } else {
            return Response.ok().entity(ErrorDTO.builder()
                            .code(404)
                            .message("No routes were found")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @GET
    @Path("/distance/greater-than/{distanceValue}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutesWithDistanceGreaterThanValueCount(@PathParam("distanceValue") Long distanceValue) {
        return Response.ok()
                .entity(additionalRouteService.getRoutesWithDistanceGreaterThanValueCount(distanceValue))
                .build();
    }

    @GET
    @Path("/distance/greater-than/{distanceValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutesWithDistanceGreaterThanValue(@QueryParam("page") Integer page,
                                                          @QueryParam("limit") Integer limit,
                                                          @PathParam("distanceValue") Long distanceValue) {
        PageDTO pageDTO = PageDTO.builder()
                .page(page)
                .limit(limit)
                .build();
        List<RouteDTO> result = additionalRouteService.getRoutesWithDistanceGreaterThanValue(distanceValue, pageDTO);
        if (!result.isEmpty())  {
            return Response.ok()
                    .entity(result)
                    .build();
        } else {
            return Response.ok().entity(ErrorDTO.builder()
                            .code(404)
                            .message("No routes were found")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }
}
