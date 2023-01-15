package com.itmo.soa.routeservice.repository;

import model.entity.Coordinates;
import model.entity.Location;
import model.entity.NamedLocation;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

//    public Coordinates getByXAndY(long x, double y) {
//        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
//        CriteriaQuery<Coordinates> criteriaQuery = builder.createQuery(Coordinates.class);
//        Root<Coordinates> root = criteriaQuery.from(Coordinates.class);
//        criteriaQuery.where(builder.equal(root.get("x_coordinate"), x))
//                .where(builder.equal(root.get("y_coordinate"), y));
//        return provider.getEntityManager().createQuery(criteriaQuery).getResultList().get(0);
//    }
//
//    public boolean isPresent(long x, double y) {
//        return getByXAndY(x, y) == null;
//    }
//
//    public void save(Coordinates coordinates) {
//        if (!isPresent(coordinates.getX_coordinate(), coordinates.getY_coordinate())) {
//            provider.getEntityManager().persist(coordinates);
//            provider.getEntityManager().flush();
//        }
//    }
}
