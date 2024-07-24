package command.patron;

import java.util.List;

import command.Command;
import exception.NonEmptyBranchException;
import service.PatronService;

public class PrintPatronCommand implements Command {

    private final PatronService patronService;

    public PrintPatronCommand(PatronService patronService) {
        this.patronService = patronService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: PRINT-PATRON [patronId]");
        }

        var id = args.get(0);

        var patron = patronService.getPatron(id);
        System.out.println(patron);
    }
}
