package com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter;

import com.lldpractice.designpatterns.structural.adapter.logger.service.DatabaseLogger;
import com.lldpractice.designpatterns.structural.adapter.logger.util.LogFormatter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseLoggerAdapter implements LogService {
    private final DatabaseLogger databaseLogger;

    @Override
    public void logInfo(String message) {
        databaseLogger.insertLog("INFO", LogFormatter.addTimestamp(message));
    }

    @Override
    public void logWarning(String message) {
        databaseLogger.insertLog("ERROR", LogFormatter.addTimestamp(message));

    }

    @Override
    public void logError(String message) {
        databaseLogger.insertLog("CRITICAL", LogFormatter.addTimestamp(message));
    }
}
