package com.lldpractice.designpatterns.structural.adapter.logger.service;

public class DatabaseLogger {
    private String tableName;
    
    public DatabaseLogger(String tableName) {
        this.tableName = tableName;
    }
    
    public void insertLog(String severity, String content) {
        // Severity: "DEBUG", "INFO", "ERROR", "CRITICAL"
        System.out.println("[DB TABLE: " + tableName + "] " + severity + " -> " + content);
    }
}
