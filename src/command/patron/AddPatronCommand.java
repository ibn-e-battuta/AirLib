package command.patron;

import java.util.List;

import command.Command;
import command.CommandInputProcessor;
import service.PatronService;

public class AddPatronCommand implements Command {
    private final PatronService patronService;

    public AddPatronCommand(PatronService patronService) {
        this.patronService = patronService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: ADD-PATRON [name] [email] [preferences]");
        }

        final String name = CommandInputProcessor.processToken(args.get(0));
        final String email = args.get(1);
        final List<String> preferences = CommandInputProcessor.processListToken(args.get(2));

        patronService.addPatron(name, email, preferences);
    }
}