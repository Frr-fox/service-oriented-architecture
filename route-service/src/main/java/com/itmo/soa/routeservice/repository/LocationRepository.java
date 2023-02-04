package com.itmo.soa.routeservice.repository;

import model.entity.Coordinates;
import model.entity.Location;
import model.entity.NamedLocation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class LocationRepository {
    @Inject
    EntityManagerProvider provider;

    public Location getLocationById(long id) {
        provider.getEntityManager().getTransaction().begin();
        var location = provider.getEntityManager().find(Location.class, id);
        provider.getEntityManager().getTransaction().commit();
        return location;
    }

    public NamedLocation getNamedLocationById(long id) {
        provider.getEntityManager().getTransaction().begin();
        var location = provider.getEntityManager().find(NamedLocation.class, id);
        provider.getEntityManager().getTransaction().commit();
        return location;
    }

    public List<Location> getByXAndYAndZ(float x, long y, int z) {
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Location> criteriaQuery = builder.createQuery(Location.class);
        Root<Location> root = criteriaQuery.from(Location.class);
        criteriaQuery.where(builder
                .and(
                        builder.equal(root.get("x_coordinate"), x),
                        builder.equal(root.get("y_coordinate"), y),
                        builder.equal(root.get("z_coordinate"), z)
                ));
        return provider.getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public boolean isPresent(float x, long y, int z) {
        return !getByXAndYAndZ(x, y, z).isEmpty();
    }

    public List<NamedLocation> getByXAndYAndZAndName(long x, int y, long z, String name) {
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<NamedLocation> criteriaQuery = builder.createQuery(NamedLocation.class);
        Root<NamedLocation> root = criteriaQuery.from(NamedLocation.class);
        criteriaQuery.where(builder
                .and(
                        builder.equal(root.get("x_coordinate"), x),
                        builder.equal(root.get("y_coordinate"), y),
                        builder.equal(root.get("z_coordinate"), z),
                        builder.equal(root.get("name"), name)
                ));
        return provider.getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public boolean isPresent(long x, int y, long z, String name) {
        return !getByXAndYAndZAndName(x, y, z, name).isEmpty();
    }
}
