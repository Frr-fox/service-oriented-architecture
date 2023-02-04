package model.exception;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String id) {
        super("Cannot found route with id = " + id);
    }
}
