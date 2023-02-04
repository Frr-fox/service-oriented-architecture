package model.exception;

public class TicketNotUniqueException extends RuntimeException {
    public TicketNotUniqueException() {
        super("Ticket must have unique route_id, departure_date and place");
    }
}
