package com.itmo.soa.routeservice.repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityManagerProvider {
//    @PersistenceContext(unitName = "route-service")
    private final EntityManager entityManager = Persistence.createEntityManagerFactory("route-service").createEntityManager();

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
