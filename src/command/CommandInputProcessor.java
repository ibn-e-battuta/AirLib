package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandInputProcessor {
    private CommandInputProcessor() {
    }

    public static String processToken(String token) {
        return token.replace('_', ' ');
    }

    public static List<String> processListToken(String token) {
        return Arrays.stream(token.split(","))
                .map(CommandInputProcessor::processToken)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
