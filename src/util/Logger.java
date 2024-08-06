package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "air-lib.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum LogLevel {
        INFO, WARNING, ERROR, DEBUG
    }

    private LogLevel minFileLevel;

    public Logger(LogLevel minFileLevel) {
        this.minFileLevel = minFileLevel;
        clearLogFile();
    }

    public void setMinFileLevel(LogLevel minFileLevel) {
        this.minFileLevel = minFileLevel;
    }

    public void log(LogLevel level, String message) {
        if (level.ordinal() >= minFileLevel.ordinal()) {
            String logEntry = formatLogEntry(level, message);
            writeToFile(logEntry);
        }
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void exception(String message, Exception e) {
        String exceptionMessage = message + ": " + e.getMessage();
        log(LogLevel.ERROR, exceptionMessage);
        writeExceptionToFile(e);
    }

    public void consoleInput(String message) {
        final String prompt = Constants.BLUE + message + Constants.RESET;
        System.out.print(prompt);
        System.out.print(Constants.INPUT_INDICATOR);
        // Note: We don't log the input indicator to the file
        writeToFile(formatLogEntry(LogLevel.INFO, "CONSOLE INPUT: " + prompt));
    }

    // Method for console output (also logs to file)
    public void console(String message) {
        System.out.println(message);
        writeToFile(formatLogEntry(LogLevel.INFO, "CONSOLE OUTPUT: " + message));
    }

    public void console() {
        System.out.println();
        writeToFile(formatLogEntry(LogLevel.INFO, "CONSOLE OUTPUT: " + '\n'));
    }

    public void consoleMessage(String message) {
        consoleLog(LogLevel.INFO, Constants.GREEN, message, "CONSOLE MESSAGE");
    }

    public void consoleHelp(String message) {
        consoleLog(LogLevel.INFO, Constants.YELLOW, message, "CONSOLE HELP");
    }

    public void consoleError(String message) {
        consoleLog(LogLevel.ERROR, Constants.RED, message, "CONSOLE ERROR");
    }


    // Method for colored console output (also logs to file)
    private void consoleLog(LogLevel logLevel, String color, String message, String console) {
        System.out.println(color + message + Constants.RESET);
        writeToFile(formatLogEntry(logLevel, console + ": " + message));
    }

    private String formatLogEntry(LogLevel level, String message) {
        return String.format("[%s] %s - %s", LocalDateTime.now().format(formatter), level, message);
    }

    private void writeExceptionToFile(Exception exception) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(formatLogEntry(LogLevel.ERROR, "Exception stack trace:"));
            exception.printStackTrace(out);
        } catch (IOException e) {
            System.err.println("Failed to write exception to log file: " + e.getMessage());
        }
    }

    private void writeToFile(String logEntry) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(logEntry);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    private void clearLogFile() {
        try {
            Files.write(Paths.get(LOG_FILE), new byte[0], StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to clear log file: " + e.getMessage());
        }
    }

    public void consoleOutput(String message) {
        consoleLog(LogLevel.INFO, Constants.WHITE_BOLD_BRIGHT, message, "CONSOLE OUTPUT");
    }
}