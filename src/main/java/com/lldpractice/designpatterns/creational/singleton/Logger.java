package com.lldpractice.designpatterns.creational.singleton;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public class Logger {

    private Logger() {

    }

    private static class SingletonLoggerHelper {
        private static final Logger loggerInstance = new Logger();
    }

    public static Logger getInstance() {
        return SingletonLoggerHelper.loggerInstance;
    }

    public void info(String message) {
        log(LogLevel.INFO.name(), message);
    }

    public void error(String message) {
        log(LogLevel.ERROR.name(), message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING.name(), message);
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String fullMessage = "[" + timestamp + "] " + "[" + level + "] " + message;

        // To reliably demonstrate a race condition, we print character by character.
        // This simulates a slower operation and gives the thread scheduler
        // many opportunities to interrupt one thread and switch to another.
        for (char c : fullMessage.toCharArray()) {
            System.out.print(c);
        }
        System.out.println(); // Print a newline at the end.
    }
}