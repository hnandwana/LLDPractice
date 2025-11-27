package com.lldpractice.designpatterns.structural.adapter.logger.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatter {
    public static String addTimestamp(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + now.format(formatter) + "] " + message;
    }
}
