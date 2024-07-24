package command;

import service.ReservationService;

import java.util.List;

public class CheckOverdueBooksCommand implements Command {
    private final ReservationService reservationService;

    public CheckOverdueBooksCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void execute(List<String> args) {
        reservationService.checkOverdueBooks();
        System.out.println("Overdue books checked and notifications sent.");
    }
}

