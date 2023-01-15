package com.itmo.soa.routeservice.service.impl;

import com.itmo.soa.routeservice.repository.AdditionalRouteRepository;
import com.itmo.soa.routeservice.service.AdditionalRouteService;
import model.converter.RouteDTOConverter;
import model.dto.PageDTO;
import model.dto.RouteDTO;
import model.entity.Route;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AdditionalRouteServiceImpl implements AdditionalRouteService {
    @Inject
    private AdditionalRouteRepository additionalRouteRepository;

    @Inject
    private RouteDTOConverter converter;

    @Override
    public RouteDTO getRouteWithMinTo() {
        Route route = additionalRouteRepository.getByToMin();
        if (route != null) {
            return converter.convertFrom(route);
        } else {
            return null;
        }
    }

    @Override
    public Integer getRoutesWithDistanceGreaterThanValueCount(Long distance) {
        return additionalRouteRepository.getByDistanceGreaterThanCount(distance);
    }

    @Override
    public List<RouteDTO> getRoutesWithDistanceGreaterThanValue(Long distance, PageDTO pageDTO) {
        List<Route> entityList = additionalRouteRepository.getByDistanceGreaterThanOnPage(distance,
                pageDTO.getPage(),
                pageDTO.getLimit());
        List<RouteDTO> responseList = new ArrayList<>();
        for (Route route : entityList) {
            responseList.add(converter.convertFrom(route));
        }
        return responseList;
    }
}
