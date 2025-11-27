package com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter;

import com.lldpractice.designpatterns.structural.adapter.logger.service.ConsoleLogger;
import com.lldpractice.designpatterns.structural.adapter.logger.util.LogFormatter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleLoggerAdapter implements LogService {

    private final ConsoleLogger consoleLogger;

    @Override
    public void logInfo(String message) {
        consoleLogger.log("INFO",LogFormatter.addTimestamp(message));
    }

    @Override
    public void logWarning(String message) {
        consoleLogger.log("WARN",LogFormatter.addTimestamp(message));

    }

    @Override
    public void logError(String message) {
        consoleLogger.log("ERROR",LogFormatter.addTimestamp(message));

    }
}
