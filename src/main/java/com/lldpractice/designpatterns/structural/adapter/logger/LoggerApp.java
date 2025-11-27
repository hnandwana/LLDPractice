package com.lldpractice.designpatterns.structural.adapter.logger;

import com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter.ConsoleLoggerAdapter;
import com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter.DatabaseLoggerAdapter;
import com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter.FileLoggerAdapter;
import com.lldpractice.designpatterns.structural.adapter.logger.loggeradapter.LogService;
import com.lldpractice.designpatterns.structural.adapter.logger.service.ConsoleLogger;
import com.lldpractice.designpatterns.structural.adapter.logger.service.DatabaseLogger;
import com.lldpractice.designpatterns.structural.adapter.logger.service.FileLogger;

public class LoggerApp {
    public static void main(String[] args) {
        String message = "User authentication failed";

        LogService fileLog = new FileLoggerAdapter(new FileLogger("app.log"));
        LogService consoleLog = new ConsoleLoggerAdapter(new ConsoleLogger());
        LogService dbLog = new DatabaseLoggerAdapter(new DatabaseLogger("logs"));

        fileLog.logError(message);
        consoleLog.logWarning(message);
        dbLog.logInfo(message);}
}
