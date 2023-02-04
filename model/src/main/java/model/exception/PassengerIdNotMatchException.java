package model.exception;

public class PassengerIdNotMatchException extends RuntimeException {
    public PassengerIdNotMatchException() {
        super("Passenger ID doesn't exist or doesn't match with actual data");
    }
}
