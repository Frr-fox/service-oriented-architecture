package com.itmo.soa.routeservice.resource;

import com.itmo.soa.routeservice.service.RouteService;
import model.dto.ErrorDTO;
import model.dto.PageDTO;
import model.dto.RouteDTO;
import model.dto.filter.RouteFilterDTO;
import model.request.RouteCreateRequest;
import model.request.RouteUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("/routes")
public class RouteServiceResource {
    private final Logger logger = LoggerFactory.getLogger(RouteServiceResource.class);
    @Inject
    private RouteService routeService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRoute(RouteCreateRequest request) {
        logger.info("Request to create route: " + request.toString());
        if (routeService.createRoute(request)) {
            return Response.ok().build();
        } else {
            return Response.status(404).entity(ErrorDTO.builder()
                            .code(404)
                            .message("Routes with the specified ID not found")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @GET
    @Path("{routeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRouteById(@PathParam("routeId") Long routeId) {
        logger.info("Request to get object by id");
        var result = routeService.getRouteById(routeId);
        if (result == null) {
            return Response.status(404).entity(ErrorDTO.builder()
                            .code(404)
                            .message("Routes with the specified ID not found")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        } else {
            return Response.ok().entity(routeService.getRouteById(routeId)).build();
        }
    }

    @PUT
    @Path("{routeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRouteById(@PathParam("routeId") Long routeId, RouteUpdateRequest request) {
        logger.info("Request to update object by id: " + request.toString());
        logger.info(request.toString());
        if (request.validate()) {
                if (routeService.updateRouteById(routeId, request)) {
                    return Response.ok().entity(routeService.getRouteById(routeId)).build();
                } else {
                    return Response.status(404).entity(
                            RouteDTO.builder()
                                    .error(ErrorDTO.builder()
                                            .code(404)
                                            .message("Routes with the specified ID not found")
                                            .time(LocalDateTime.now())
                                            .build()
                                    )
                            )
                            .build();
                }
        } else {
            return Response.status(400).entity(ErrorDTO.builder()
                            .code(400)
                            .message("Input value is invalid or incorrect")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @DELETE
    @Path("{routeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRouteById(@PathParam("routeId") Long routeId) {
        logger.info("Request to delete object by id");
        if (routeService.deleteRouteById(routeId)) {
            return Response.ok().build();
        } else {
            return Response.status(404).entity(ErrorDTO.builder()
                            .code(404)
                            .message("Routes with the specified ID not found")
                            .time(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutes(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit) {
        PageDTO pageDTO = PageDTO.builder()
                .page(page)
                .limit(limit)
                .build();
        return Response.ok().entity(routeService.getAllRoutes(pageDTO)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutes() {
        return Response.ok().entity(routeService.getAllRoutes(new PageDTO(0, 0))).build();
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutesWithFilter(@QueryParam("id") Long id, @QueryParam("name") String name,
                                        @QueryParam("x") Long x, @QueryParam("y") Double y,
                                        @QueryParam("creationDate") String creationDate,
                                        @QueryParam("fromX") Float fromX, @QueryParam("fromY") Long fromY,
                                        @QueryParam("fromZ") Integer fromZ, @QueryParam("toX") Long toX,
                                        @QueryParam("toY") Integer toY, @QueryParam("toZ") Long toZ,
                                        @QueryParam("toName") String toName, @QueryParam("distance") Long distance,
                                        @QueryParam("sortby") String sortBy, @QueryParam("order") String order,
                                        @QueryParam("page") Integer page, @QueryParam("limit") Integer limit) {
        RouteFilterDTO filterDTO = RouteFilterDTO.builder()
                .id(id)
                .name(name)
                .x(x)
                .y(y)
                .creationDate(creationDate)
                .fromX(fromX)
                .fromY(fromY)
                .fromZ(fromZ)
                .toX(toX)
                .toY(toY)
                .toZ(toZ)
                .toName(toName)
                .distance(distance)
                .sortBy(sortBy)
                .order(order)
                .page(page)
                .limit(limit)
                .build();
        return Response.ok(routeService.getAllRoutesWithFilter(filterDTO)).build();
    }
}