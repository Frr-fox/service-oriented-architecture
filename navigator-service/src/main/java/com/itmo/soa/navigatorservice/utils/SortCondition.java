package com.itmo.soa.navigatorservice.utils;

import model.dto.RouteDTO;

import java.util.Comparator;

public enum SortCondition {
    ID("id", "id", Comparator.comparing(RouteDTO::getId)),
    NAME("name", "name", Comparator.comparing(RouteDTO::getName)),
    COORDINATES("coordinates", "coordinates", Comparator.comparing(RouteDTO::getIdCoordinate)),
    CREATION_DATE("creation-date", "creationDate", Comparator.comparing(RouteDTO::getCreationDate)),
    FROM("form", "from", Comparator.comparing(RouteDTO::getFromId)),
    TO("to", "to", Comparator.comparing(RouteDTO::getToId)),
    DISTANCE("distance", "distance", Comparator.comparing(RouteDTO::getDistance));
    private final String condition;
    private final String tableField;
    private final Comparator<RouteDTO> comparator;

    SortCondition(String condition, String tableField, Comparator<RouteDTO> comparator) {
        this.condition = condition;
        this.tableField = tableField;
        this.comparator = comparator;
    }

    public static SortCondition convert(String condition) {
        switch (condition) {
            case "id":
                return ID;
            case "name":
                return NAME;
            case "coordinates":
                return COORDINATES;
            case "creation-date":
                return CREATION_DATE;
            case "from":
                return FROM;
            case "to":
                return TO;
            case "distance":
                return DISTANCE;
            default:
                return null;
        }
    }

    public Comparator<RouteDTO> getComparator() {
        return comparator;
    }
}
