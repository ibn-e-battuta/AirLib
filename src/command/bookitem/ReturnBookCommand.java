package command.bookitem;

import model.Reservation;
import service.ReservationService;

import java.util.List;

import command.Command;

public class ReturnBookCommand implements Command {
    private final ReservationService reservationService;

    public ReturnBookCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: RETURN-BOOK <reservationId>");
        }

        String reservationId = args.get(0);
        Reservation reservation = reservationService.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        reservationService.returnBook(reservation);
        System.out.println("Book returned successfully.");
    }
}
