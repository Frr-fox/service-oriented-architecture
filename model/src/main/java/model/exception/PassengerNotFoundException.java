package model.exception;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String passengerId) {
        super("Passenger with ID = " + passengerId + " doesn't found");
    }
}
