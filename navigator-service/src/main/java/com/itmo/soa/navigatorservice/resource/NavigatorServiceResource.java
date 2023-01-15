package com.itmo.soa.navigatorservice.resource;

import com.itmo.soa.navigatorservice.service.NavigatorService;
import com.itmo.soa.navigatorservice.service.NavigatorServiceImpl;
import com.itmo.soa.navigatorservice.utils.SortCondition;
import model.dto.ErrorDTO;
import model.dto.RouteDTO;
import model.request.RouteCreateBetweenLocationsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/navigator")
public class NavigatorServiceResource {
    private final Logger logger = LoggerFactory.getLogger(NavigatorServiceResource.class);
    @Inject
    private NavigatorService navigatorService;

    private boolean validateBetweenLocationParameters(Long idLocationFrom, Long idLocationTo, String sortCondition,
                                              Integer page, Integer limit) {
        boolean mainParameters = idLocationFrom > 0 && idLocationTo > 0 && SortCondition.convert(sortCondition) != null;
        boolean pagination = page > 0 && limit > 0;
        return mainParameters && pagination;
    }

    @GET
    @Path("routes/{id-from}/{id-to}/{order-by}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoutesBetweenLocationsByIdSorted(@PathParam("id-from") Long idLocationFrom,
                                                           @PathParam("id-to") Long idLocationTo,
                                                           @PathParam("order-by") String sortCondition,
                                                           @QueryParam("page") Integer page,
                                                           @QueryParam("limit") Integer limit) {
        if (!validateBetweenLocationParameters(idLocationFrom, idLocationTo, sortCondition, page, limit)) {
            return Response.ok().entity(ErrorDTO.builder()
                            .code(400)
                            .message("Invalid parameters supplied")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
        List<RouteDTO> result = navigatorService.getAllRoutesBetweenLocationsByIdSorted(
                idLocationFrom,
                idLocationTo,
                sortCondition,
                page,
                limit);
        if (!result.isEmpty()) {
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

    @POST
    @Path("route/add/{id-from}/{id-to}/{distance}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRouteBetweenLocations(@PathParam("id-from") Long idLocationFrom,
                                                @PathParam("id-to") Long idLocationTo,
                                                @PathParam("distance") Long distance,
                                                RouteCreateBetweenLocationsRequest request) {
        if (idLocationFrom > 0 && idLocationTo > 0 && request.validate()) {
            navigatorService.createRouteBetweenLocations(idLocationFrom, idLocationTo, distance, request);
            return Response.ok().build();
        } else {
            logger.warn("Validation failed");
            return Response.ok().entity(ErrorDTO.builder()
                            .code(400)
                            .message("Invalid parameters supplied")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }
}