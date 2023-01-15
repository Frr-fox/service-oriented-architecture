package com.itmo.soa.routeservice.service;

import model.dto.PageDTO;
import model.dto.RouteDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AdditionalRouteService {

    RouteDTO getRouteWithMinTo();

    Integer getRoutesWithDistanceGreaterThanValueCount(Long distance);

    List<RouteDTO> getRoutesWithDistanceGreaterThanValue(Long distance, PageDTO pageDTO);
}
