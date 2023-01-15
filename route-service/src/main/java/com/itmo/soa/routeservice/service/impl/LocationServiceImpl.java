package com.itmo.soa.routeservice.service.impl;

import com.itmo.soa.routeservice.repository.LocationRepository;
import com.itmo.soa.routeservice.service.LocationService;
import model.entity.Location;
import model.entity.NamedLocation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LocationServiceImpl implements LocationService {
    @Inject
    private LocationRepository locationRepository;

    @Override
    public Location getLocationById(long id) {
        return locationRepository.getLocationById(id);
    }

    @Override
    public NamedLocation getNamedLocationById(long id) {
        return locationRepository.getNamedLocationById(id);
    }
}
