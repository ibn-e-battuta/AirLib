package command.patron;

import java.util.List;

import command.Command;
import command.CommandInputProcessor;
import service.PatronService;

public class UpdatePatronCommand implements Command {
    private final PatronService patronService;

    public UpdatePatronCommand(PatronService patronService) {
        this.patronService = patronService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 4) {
            throw new IllegalArgumentException("Usage: UPDATE-PATRON [patronId] [name] [email] [preferences]");
        }

        final String patronId = args.get(0);
        final String name = CommandInputProcessor.processToken(args.get(1));
        final String email = args.get(2);
        final List<String> preferences = CommandInputProcessor.processListToken(args.get(3));

        patronService.updatePatron(patronId, name, email, preferences);
    }
}