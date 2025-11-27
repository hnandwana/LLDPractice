package com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter;

public interface LogService {
    void logInfo(String message);
    void logWarning(String message);
    void logError(String message);
}
