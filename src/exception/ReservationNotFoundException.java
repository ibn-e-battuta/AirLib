package exception;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException(String reservationId) {
        super("Reservation with Id: " + reservationId + " not found");
    }
}
