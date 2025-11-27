package com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter;


import com.lldpractice.designpatterns.structural.adapter.logger.service.FileLogger;
import com.lldpractice.designpatterns.structural.adapter.logger.util.LogFormatter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileLoggerAdapter implements LogService {
    private final FileLogger fileLogger;

    @Override
    public void logInfo(String message) {
        fileLogger.writeToFile(LogFormatter.addTimestamp(message), 1);
    }

    @Override
    public void logWarning(String message) {
        fileLogger.writeToFile(LogFormatter.addTimestamp(message), 2);
    }

    @Override
    public void logError(String message) {
        fileLogger.writeToFile(LogFormatter.addTimestamp(message), 3);
    }
}
