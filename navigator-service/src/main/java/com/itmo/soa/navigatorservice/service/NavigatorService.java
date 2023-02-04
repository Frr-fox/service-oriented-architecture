package com.itmo.soa.navigatorservice.service;

import model.request.RouteCreateBetweenLocationsRequest;
import model.dto.RouteDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface NavigatorService {
    List<RouteDTO> getAllRoutesBetweenLocationsByIdSorted(Long idLocationFrom,
                                                          Long idLocationTo,
                                                          String sortCondition,
                                                          Integer page,
                                                          Integer limit);

    boolean createRouteBetweenLocations(Long idLocationFrom,
                                     Long idLocationTo,
                                     Long distance,
                                     RouteCreateBetweenLocationsRequest request);
}
