package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class Logger {
    private Consumer<String> println = System.out::println;
    private static final String LOG_FILE = "air-lib.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum LogLevel {
        INFO, WARNING, ERROR, DEBUG
    }

    private LogLevel minLevel;
    private boolean logToConsole;

    public Logger(LogLevel minLevel, boolean logToConsole) {
        this.minLevel = minLevel;
        this.logToConsole = logToConsole;
        clearLogFile();
    }

    public void setMinLevel(LogLevel minLevel) {
        this.minLevel = minLevel;
    }

    public void setLogToConsole(boolean logToConsole) {
        this.logToConsole = logToConsole;
    }

    public void log(LogLevel level, String message) {
        if (level.ordinal() >= minLevel.ordinal()) {
            String logEntry = String.format("[%s] %s - %s",
                    LocalDateTime.now().format(formatter), level, message);
            writeToFile(logEntry);
            if (logToConsole) {
                println.accept(logEntry);
            }
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

    private void writeExceptionToFile(Exception exception) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            exception.printStackTrace(out);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
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
            Files.write(Paths.get(LOG_FILE), new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to clear log file: " + e.getMessage());
        }
    }
}