package command.patron;

import java.util.List;

import command.Command;
import service.PatronService;

public class UpdatePatronCommand implements Command {
    private final PatronService patronService;

    public UpdatePatronCommand(PatronService patronService) {
        this.patronService = patronService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: UPDATE-PATRON [patronId] [name] [email]");
        }

        String id = args.get(0);
        String name = args.get(1);
        String email = args.get(2);

        patronService.updatePatron(id, name, email);
    }
}
