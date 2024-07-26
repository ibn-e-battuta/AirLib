package exception;

public class ReservationStatusException extends Exception {
    public ReservationStatusException() {
        super("Reservation cannot be cancelled");
    }
}
