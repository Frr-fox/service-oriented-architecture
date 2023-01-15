package com.itmo.soa.routeservice.service;

import com.itmo.soa.routeservice.exception.RouteNotFoundException;
import model.dto.*;
import model.dto.filter.RouteFilterDTO;
import model.request.RouteCreateRequest;
import model.request.RouteUpdateRequest;
import model.response.RouteSearchResponse;

import javax.ejb.Local;

@Local
public interface RouteService {
    boolean createRoute(RouteCreateRequest request);

    RouteDTO getRouteById(Long routeId);

    boolean updateRouteById(Long routeId, RouteUpdateRequest request);

    boolean deleteRouteById(Long routeId);

    RouteSearchResponse getAllRoutes(PageDTO pageDTO);

    RouteSearchResponse getAllRoutesWithFilter(RouteFilterDTO filterDTO);
}
