package com.lldpractice.designpatterns.structural.proxy.document;

import java.nio.file.AccessDeniedException;

public interface Document {
    void view() throws AccessDeniedException;
    void edit(String content) throws AccessDeniedException;
    void delete() throws AccessDeniedException;
    String getMetadata() throws AccessDeniedException;
}
