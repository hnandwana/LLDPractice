package com.lldpractice.designpatterns.structural.proxy.proxies;

import com.lldpractice.designpatterns.structural.proxy.document.Document;
import com.lldpractice.designpatterns.structural.proxy.document.RealDocument;
import lombok.RequiredArgsConstructor;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
public class VirtualDocumentProxy implements Document {
    private RealDocument document;
    private final String fileName;

    @Override
    public void view() throws AccessDeniedException {
        if (document == null) {
            document = new RealDocument(fileName);
        }
        document.view();
    }

    @Override
    public void edit(String content) throws AccessDeniedException {
        if (document == null) {
            document = new RealDocument(fileName);
        }
        document.edit(content);
    }

    @Override
    public void delete() throws AccessDeniedException {
        if (document == null) {
            document = new RealDocument(fileName);
        }
        document.delete();
    }

    @Override
    public String getMetadata() {
        return "Metadata: " + fileName;
    }
}
