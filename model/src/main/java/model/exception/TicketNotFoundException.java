package model.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String id) {
        super("Ticket with ID = " + id + " doesn't found");
    }
}
