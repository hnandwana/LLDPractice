package com.lldpractice.designpatterns.structural.adapter.logger.service;

public class ConsoleLogger {
    public void log(String level, String msg) {
        // Level: "INFO", "WARN", "ERROR"
        System.out.println("[CONSOLE] " + level + " - " + msg);
    }
}
