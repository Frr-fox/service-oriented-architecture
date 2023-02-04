package com.itmo.soa.routeservice.repository;

import model.entity.Coordinates;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CoordinatedRepository {
    @Inject
    EntityManagerProvider provider;

    public List<Coordinates> getByXAndY(long x, double y) {
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Coordinates> criteriaQuery = builder.createQuery(Coordinates.class);
        Root<Coordinates> root = criteriaQuery.from(Coordinates.class);
        criteriaQuery.where(builder.
                and(
                        builder.equal(root.get("x_coordinate"), x),
                        builder.equal(root.get("y_coordinate"), y)
                ));
        return provider.getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public boolean isPresent(long x, double y) {
        return !getByXAndY(x, y).isEmpty();
    }
}
