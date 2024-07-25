package command.util;

import command.Command;

import java.util.List;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, String> _commandDescriptions;

    public HelpCommand(Map<String, String> commandDescriptions) {
        _commandDescriptions = commandDescriptions;
    }

    @Override
    public void execute(List<String> args) {
        System.out.println("Available commands:");
        int maxCommandLength = _commandDescriptions.keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
        for (Map.Entry<String, String> entry : _commandDescriptions.entrySet()) {
            System.out.printf("%-" + (maxCommandLength + 4) + "s - %s%n", entry.getKey(), entry.getValue());
        }
        System.out.printf("%-" + (maxCommandLength + 4) + "s - %s%n", "EXIT", "Exit the program");
    }
}
