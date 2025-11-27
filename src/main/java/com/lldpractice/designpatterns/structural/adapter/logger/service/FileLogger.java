package com.lldpractice.designpatterns.structural.adapter.logger.service;

public class FileLogger {
    private String filename;
    
    public FileLogger(String filename) {
        this.filename = filename;
    }
    
    public void writeToFile(String message, int priority) {
        // Priority: 1=Low, 2=Medium, 3=High
        System.out.println("[FILE: " + filename + "] Priority " + priority + ": " + message);
    }
}


