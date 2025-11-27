package com.lldpractice.designpatterns.structural.proxy.document;

import lombok.Getter;

@Getter
public class RealDocument implements Document {
    private String filename;
    private String content;
    
    public RealDocument(String filename) {
        this.filename = filename;
        loadFromDisk();  // Expensive operation!
    }
    
    private void loadFromDisk() {
        System.out.println("Loading document from disk: " + filename);
        try {
            Thread.sleep(2000);  // Simulate 2-second load time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.content = "Content of " + filename;  // Simulate loaded content
        System.out.println("Document loaded: " + filename);
    }
    
    @Override
    public void view() {
        System.out.println("Viewing document: " + filename);
        System.out.println(content);
    }
    
    @Override
    public void edit(String newContent) {
        System.out.println("Editing document: " + filename);
        this.content = newContent;
    }
    
    @Override
    public void delete() {
        System.out.println("Deleting document: " + filename);
        this.content = null;
    }
    
    @Override
    public String getMetadata() {
        return "Metadata: " + filename;
    }
}
