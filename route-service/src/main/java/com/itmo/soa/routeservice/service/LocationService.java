package com.itmo.soa.routeservice.service;

import model.entity.Location;
import model.entity.NamedLocation;

import javax.ejb.Local;

@Local
public interface LocationService {
    Location getLocationById(long id);

    NamedLocation getNamedLocationById(long id);
}
