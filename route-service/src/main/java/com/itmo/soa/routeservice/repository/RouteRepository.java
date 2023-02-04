package com.itmo.soa.routeservice.repository;

import model.entity.Route;
import model.entity.Ticket;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class RouteRepository {
    @Inject
    EntityManagerProvider provider;

    public Route getById(Long id) {
        provider.getEntityManager().getTransaction().begin();
        var route = provider.getEntityManager().find(Route.class, id);
        provider.getEntityManager().getTransaction().commit();
        return route;
    }

    public void save(Route route) {
        provider.getEntityManager().getTransaction().begin();
        provider.getEntityManager().persist(route);
        provider.getEntityManager().flush();
        provider.getEntityManager().getTransaction().commit();
    }

    public void update(Route route) {
        provider.getEntityManager().getTransaction().begin();
        provider.getEntityManager().merge(route);
        provider.getEntityManager().flush();
        provider.getEntityManager().getTransaction().commit();
    }

    public List<Route> getAllOnPage(Integer pageNumber, Integer maxEntities) {
        provider.getEntityManager().getTransaction().begin();
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Route> criteriaQuery = builder.createQuery(Route.class);
        criteriaQuery.select(criteriaQuery.from(Route.class));
        List<Route> routes;
        if (pageNumber == 0 && maxEntities == 0) {
            routes = provider.getEntityManager().createQuery(criteriaQuery)
                    .getResultList();
        } else {
            routes = provider.getEntityManager().createQuery(criteriaQuery)
                    .setFirstResult((pageNumber - 1) * maxEntities)
                    .setMaxResults(maxEntities)
                    .getResultList();
        }
        provider.getEntityManager().getTransaction().commit();
        return routes;
    }

    public void removeById(Long id) {
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Ticket> criteriaQuery = builder.createQuery(Ticket.class);
        criteriaQuery.select(criteriaQuery.from(Ticket.class));
        List<Ticket> tickets = provider.getEntityManager().createQuery(criteriaQuery).getResultList();
        CriteriaDelete<Ticket> criteriaDeleteTickets = builder.createCriteriaDelete(Ticket.class);
        Root<Ticket> rootTicket = criteriaDeleteTickets.from(Ticket.class);
        if (!tickets.isEmpty()) {
            criteriaDeleteTickets.where(builder.equal(rootTicket.get("direction"), id));
        }
        CriteriaDelete<Route> criteriaDelete = builder.createCriteriaDelete(Route.class);
        Root<Route> root = criteriaDelete.from(Route.class);
        criteriaDelete.where(builder.equal(root.get("id"), id));

        provider.getEntityManager().getTransaction().begin();
        if (!tickets.isEmpty()) {
            provider.getEntityManager().createQuery(criteriaDeleteTickets).executeUpdate();
        }
        provider.getEntityManager().createQuery(criteriaDelete).executeUpdate();
        provider.getEntityManager().getTransaction().commit();
    }

    public long countAll() {
        provider.getEntityManager().getTransaction().begin();
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(Route.class)));
        var count = provider.getEntityManager().createQuery(criteriaQuery).getSingleResult();
        provider.getEntityManager().getTransaction().commit();
        return count;
    }
}
