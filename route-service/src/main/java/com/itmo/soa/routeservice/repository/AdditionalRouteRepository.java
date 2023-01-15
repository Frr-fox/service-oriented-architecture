package com.itmo.soa.routeservice.repository;

import model.entity.Route;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class AdditionalRouteRepository {
    @Inject
    EntityManagerProvider provider;

    public Route getByToMin() {
        provider.getEntityManager().getTransaction().begin();
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Route> criteriaQuery = builder.createQuery(Route.class);
        Root<Route> root = criteriaQuery.from(Route.class);
        criteriaQuery.orderBy(builder.desc(root.get("to")));
        List<Route> result = provider.getEntityManager().createQuery(criteriaQuery).setMaxResults(1).getResultList();
        Route route;
        if (result.isEmpty()) {
            route = null;
        } else {
            route = result.get(0);
        }
        provider.getEntityManager().getTransaction().commit();
        return route;
    }

    public Integer getByDistanceGreaterThanCount(Long distance) {
        provider.getEntityManager().getTransaction().begin();
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Route> criteriaQuery = builder.createQuery(Route.class);
        Root<Route> root = criteriaQuery.from(Route.class);
        criteriaQuery.where(builder.greaterThan(root.get("distance"), distance));
        var count = provider.getEntityManager().createQuery(criteriaQuery).getResultList().size();
        provider.getEntityManager().getTransaction().commit();
        return count;
    }

    public List<Route> getByDistanceGreaterThanOnPage(Long distance, int pageNumber, int maxNumber) {
        provider.getEntityManager().getTransaction().begin();
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Route> criteriaQuery = builder.createQuery(Route.class);
        Root<Route> root = criteriaQuery.from(Route.class);
        criteriaQuery.where(builder.greaterThan(root.get("distance"), distance));
        var routes = provider.getEntityManager().createQuery(criteriaQuery)
                .setFirstResult((pageNumber - 1) * maxNumber)
                .setMaxResults(maxNumber)
                .getResultList();
        provider.getEntityManager().getTransaction().commit();
        return routes;
    }
}
