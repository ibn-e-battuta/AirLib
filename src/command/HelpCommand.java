package command;

import java.util.List;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, String> commandDescriptions;

    public HelpCommand(Map<String, String> commandDescriptions) {
        this.commandDescriptions = commandDescriptions;
    }

    @Override
    public void execute(List<String> args) {
        System.out.println("Available commands:");
        for (Map.Entry<String, String> entry : commandDescriptions.entrySet()) {
            System.out.printf("%-20s - %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println("EXIT                - Exit the program");
    }
}
