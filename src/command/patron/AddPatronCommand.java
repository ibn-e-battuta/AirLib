package command.patron;

import command.Command;
import command.CommandInputProcessor;
import service.PatronService;

import java.util.List;

public class AddPatronCommand implements Command {
    private final PatronService _patronService;

    public AddPatronCommand(PatronService patronService) {
        _patronService = patronService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: ADD-PATRON [name] [email] [preferences]");
        }

        var name = CommandInputProcessor.processToken(args.get(0));
        var email = args.get(1);
        var preferences = CommandInputProcessor.processListToken(args.get(2));

        _patronService.addPatron(name, email, preferences);
    }
}
