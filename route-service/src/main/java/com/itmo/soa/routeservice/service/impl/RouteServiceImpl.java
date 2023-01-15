package com.itmo.soa.routeservice.service.impl;

import com.itmo.soa.routeservice.exception.RouteNotFoundException;
import com.itmo.soa.routeservice.filter.RouteFilter;
import com.itmo.soa.routeservice.repository.LocationRepository;
import com.itmo.soa.routeservice.repository.RouteRepository;
import com.itmo.soa.routeservice.service.RouteService;
import model.dto.filter.RouteFilterDTO;
import model.dto.filter.SortByType;
import model.dto.filter.SortOrder;
import model.entity.Location;
import model.entity.NamedLocation;
import model.entity.Route;
import model.converter.RouteDTOConverter;
import model.dto.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.request.RouteCreateRequest;
import model.request.RouteUpdateRequest;
import model.response.RouteSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class RouteServiceImpl implements RouteService {
    private final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);
    @Inject
    private RouteRepository routeRepository;

    @Inject
    private LocationRepository locationRepository;

    @Inject
    private RouteDTOConverter converter;

    @Inject
    private RouteFilter filter;

    @Override
    public boolean createRoute(RouteCreateRequest request) {
        boolean validated = request.validate();
        if (validated) {
            if (request.getFromId() != null && request.getToId() != null) {
                Location from = locationRepository.getLocationById(request.getFromId());
                NamedLocation to = locationRepository.getNamedLocationById(request.getToId());
                routeRepository.save(request.convertFromWithLocations(from, to));
            } else {
                routeRepository.save(request.convertFrom());
            }
        }
        return validated;
    }

    @Override
    public RouteDTO getRouteById(Long routeId) throws RouteNotFoundException {
        Route route;
        try {
        logger.info("RouteId: " + routeId);
        route = routeRepository.getById(routeId);
        logger.info("Route: " + (route == null ? "null" : route.toString()));
            if (route == null) throw new RouteNotFoundException(routeId.toString());
        } catch (RouteNotFoundException e) {
            logger.warn(e.getMessage());
            return null;
        }
        return converter.convertFrom(route);
    }

    @Override
    @Transactional
    public boolean updateRouteById(Long routeId, RouteUpdateRequest request) {
        Route route;
        try {
            route = routeRepository.getById(routeId);
            if (route == null) {
                throw new RouteNotFoundException(routeId.toString());
            }
            if (request.getCreationDate() == null) {
                request.setCreationDate(route.getCreationDate());
            }
            Route updatedRoute = request.convertFrom();
            updatedRoute.setId(routeId);
            logger.info(updatedRoute.toString());
            routeRepository.update(updatedRoute);
            return true;
        } catch (RouteNotFoundException e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteRouteById(Long routeId) {
        Route route;
        try {
            route = routeRepository.getById(routeId);
            if (route == null) {
                throw new RouteNotFoundException(routeId.toString());
            }
            routeRepository.removeById(routeId);
            return true;
        } catch (RouteNotFoundException e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public RouteSearchResponse getAllRoutes(PageDTO pageDTO) {
        List<Route> entityList = routeRepository.getAllOnPage(pageDTO.getPage(), pageDTO.getLimit());
        List<RouteDTO> responseList = new ArrayList<>();
        for (Route route: entityList) {
            RouteDTO routeDTO = converter.convertFrom(route);
            routeDTO.setFromId(route.getFrom().getLocation_id());
            routeDTO.setToId(route.getTo().getLocation_id());
            responseList.add(routeDTO);
        }
        logger.info(entityList.toString());
        logger.info(responseList.toString());
        int pagesCount;
        if (pageDTO.getLimit() == 0) {
            pagesCount = 1;
        } else {
            pagesCount = (int) Math.ceil(routeRepository.countAll() / (double) pageDTO.getLimit());
        }
        return new RouteSearchResponse(responseList, pagesCount);
    }

    @Override
    public RouteSearchResponse getAllRoutesWithFilter(RouteFilterDTO filterDTO) {
        List<Route> routes;
        HashMap<String, String> fieldToFilter = new HashMap<>();
        if (filterDTO.getId() != null)
            fieldToFilter.put("id", filterDTO.getId().toString());
        if (filterDTO.getName() != null)
            fieldToFilter.put("name", filterDTO.getName());
        if (filterDTO.getCreationDate() != null)
            fieldToFilter.put("creationDate", filterDTO.getCreationDate());
        if (filterDTO.getX() != null)
            fieldToFilter.put("x", filterDTO.getX().toString());
        if (filterDTO.getY() != null)
            fieldToFilter.put("y", filterDTO.getY().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("fromX", filterDTO.getFromX().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("fromY", filterDTO.getFromY().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("fromZ", filterDTO.getFromZ().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("toX", filterDTO.getToX().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("toY", filterDTO.getToY().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("toZ", filterDTO.getToZ().toString());
        if (filterDTO.getFromX() != null)
            fieldToFilter.put("toName", filterDTO.getToName());

        if (filterDTO.getSortBy() != null)
            //ищем с фильтром и сортируем
            routes = filter.getAllMatchingFieldsSortedPageable(
                    fieldToFilter,
                    SortByType.valueOf(filterDTO.getSortBy()),
                    SortOrder.valueOf(filterDTO.getOrder()),
                    filterDTO.getPage(),
                    filterDTO.getLimit());
        else
            //ищем только с фильтром без сортировки
            routes = filter.getAllMatchingFieldsPageable(
                    fieldToFilter,
                    filterDTO.getPage(),
                    filterDTO.getLimit());

        List<RouteDTO> list = new ArrayList<>();
        for (Route route: routes) {
            list.add(converter.convertFrom(route));
        }
        return new RouteSearchResponse(list, filterDTO.getPage());
    }
}
