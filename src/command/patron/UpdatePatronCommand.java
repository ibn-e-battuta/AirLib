package command.patron;

import command.Command;
import command.CommandInputProcessor;
import service.PatronService;

import java.util.List;

public class UpdatePatronCommand implements Command {
    private final PatronService _patronService;

    public UpdatePatronCommand(PatronService patronService) {
        _patronService = patronService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 4) {
            throw new IllegalArgumentException("Usage: UPDATE-PATRON [patronId] [name] [email] [preferences]");
        }

        var patronId = args.get(0);
        var name = CommandInputProcessor.processToken(args.get(1));
        var email = args.get(2);
        var preferences = CommandInputProcessor.processListToken(args.get(3));

        _patronService.updatePatron(patronId, name, email, preferences);
    }
}
