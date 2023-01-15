package com.itmo.soa.routeservice.filter;

import com.itmo.soa.routeservice.repository.EntityManagerProvider;
import model.dto.filter.SortByType;
import model.dto.filter.SortOrder;
import model.entity.Route;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
public class RouteFilter {
    @Inject
    private EntityManagerProvider provider;

    public List<Route> getAllMatchingFieldsSortedPageable(Map<String, String> fieldToFilter, SortByType type,
                                                          SortOrder order, Integer page, Integer limit) {
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Route> query = builder.createQuery(Route.class);
        Root<Route> root = query.from(Route.class);
        query.select(root);

        if (order.equals(SortOrder.ASC)) {
            query.orderBy(builder.asc(sortParam(root, type.name())));
        } else
            query.orderBy(builder.desc(sortParam(root, type.name())));

        Predicate[] predicates = preparePredicatesFromFilter(builder, fieldToFilter, root);
        if (predicates.length > 0)
            query.where(predicates);

        List<Route> routes = provider.getEntityManager().createQuery(query)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
        return routes;
    }

    public List<Route> getAllMatchingFieldsPageable(Map<String, String> fieldToFilter, Integer page, Integer limit) {
        CriteriaBuilder builder = provider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Route> query = builder.createQuery(Route.class);
        Root<Route> root = query.from(Route.class);
        query.select(root);
        Predicate[] predicates = preparePredicatesFromFilter(builder, fieldToFilter, root);
        if (predicates.length > 0)
            query.where(predicates);

        List<Route> routes = provider.getEntityManager().createQuery(query)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
        return routes;
    }

    private Predicate[] preparePredicatesFromFilter(CriteriaBuilder builder, Map<String, String> fieldToVal, Root<Route> root) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : fieldToVal.entrySet()) {
            switch (entry.getKey()) {
                case "creationDate": {
                    predicates.add(builder.equal(root.get(entry.getKey()), ZonedDateTime.parse(entry.getValue())));
                    break;
                }
                case "x": {
                    Path<Object> objectPath = root.get("coordinates").get("x_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "y": {
                    Path<Object> objectPath = root.get("coordinates").get("y_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "fromX": {
                    Path<Object> objectPath = root.get("from").get("x_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "fromY": {
                    Path<Object> objectPath = root.get("from").get("y_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "fromZ": {
                    Path<Object> objectPath = root.get("from").get("z_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "toX": {
                    Path<Object> objectPath = root.get("to").get("x_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "toY": {
                    Path<Object> objectPath = root.get("to").get("y_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "toZ": {
                    Path<Object> objectPath = root.get("to").get("z_coordinate");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                case "toName": {
                    Path<Object> objectPath = root.get("to").get("name");
                    predicates.add(builder.equal(objectPath, entry.getValue()));
                    break;
                }
                default: {
                    predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
                }
            }
        }
        return predicates.toArray(new Predicate[0]);
    }

    private Path<Object> sortParam(Root<Route> root, String type) {
        Path<Object> objectPath;
        switch (type) {
            case "x": {
                objectPath = root.get("coordinates").get("x_coordinate");
                break;
            }
            case "y": {
                objectPath = root.get("coordinates").get("y_coordinate");
                break;
            }
            case "fromX": {
                objectPath = root.get("from").get("x_coordinate");
                break;
            }
            case "fromY": {
                objectPath = root.get("from").get("y_coordinate");
                break;
            }
            case "fromZ": {
                objectPath = root.get("from").get("z_coordinate");
                break;
            }
            case "toX": {
                objectPath = root.get("to").get("x_coordinate");
                break;
            }
            case "toY": {
                objectPath = root.get("to").get("y_coordinate");
                break;
            }
            case "toZ": {
                objectPath = root.get("to").get("z_coordinate");
                break;
            }
            case "toName": {
                objectPath = root.get("to").get("name");
                break;
            }
            default:
                objectPath = root.get(type);
        }
        return objectPath;
    }
}
