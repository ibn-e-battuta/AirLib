package command.patron;

import java.util.List;

import command.Command;
import service.PatronService;

public class AddPatronCommand implements Command {
    private final PatronService patronService;

    public AddPatronCommand(PatronService patronService) {
        this.patronService = patronService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: ADD-PATRON [name] [email]");
        }

        String name = args.get(0);
        String email = args.get(1);

        patronService.addPatron(name, email);
    }
}
