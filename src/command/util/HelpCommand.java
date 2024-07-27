package command.util;

import java.util.List;

import command.Command;
import command.CommandType;
import util.Constants;

public class HelpCommand implements Command {
    private final CommandType[] commandTypes;

    public HelpCommand(CommandType[] commandTypes) {
        this.commandTypes = commandTypes;
    }

    @Override
    public void execute(List<String> args) {
        if (args.isEmpty()) {
            printAllCommands();
        } else {
            String commandName = args.get(0).toUpperCase();
            printCommandHelp(commandName);
        }
    }

    private void printAllCommands() {
        System.out.println(Constants.BLUE + "Available commands:" + Constants.RESET);
        System.out.println(Constants.BLUE + "-------------------" + Constants.RESET);

        for (CommandType commandType : commandTypes) {
            printCommandDetails(commandType);
            System.out.println(); // Add a blank line between commands
        }

        System.out.println(Constants.GREEN + "EXIT" + Constants.RESET);
        System.out.println(Constants.YELLOW + "    * Exit the program" + Constants.RESET);
    }

    private void printCommandHelp(String commandName) {
        for (CommandType commandType : commandTypes) {
            if (commandType.getCommandName().equals(commandName)) {
                printCommandDetails(commandType);
                return;
            }
        }
        System.out.println(Constants.YELLOW + "Unknown command: " + commandName + Constants.RESET);
    }

    private void printCommandDetails(CommandType commandType) {
        String commandName = commandType.getCommandName();
        String parameters = commandType.getParameters();
        String description = commandType.getDescription();
        String example = commandType.getExample();

        // Print command name in green
        System.out.print(Constants.GREEN + commandName + Constants.RESET);

        // Print parameters in magenta if they exist
        if (parameters != null && !parameters.isEmpty()) {
            System.out.println(" " + Constants.MAGENTA + parameters + Constants.RESET);
        } else {
            System.out.println();
        }

        // Print description
        System.out.println(Constants.YELLOW + "    * " + description + Constants.RESET);

        // Print example if it exists
        if (example != null && !example.isEmpty()) {
            System.out.println(Constants.CYAN + "    * " + example + Constants.RESET);
        }
    }
}