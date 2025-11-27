package com.lldpractice.designpatterns.structural.proxy.proxies;

import com.lldpractice.designpatterns.structural.adapter.logger.util.LogFormatter;
import com.lldpractice.designpatterns.structural.proxy.document.Document;
import lombok.AllArgsConstructor;

import java.nio.file.AccessDeniedException;

@AllArgsConstructor
public class LoggingProxy implements Document {
    private Document document;
    private String userName;

    @Override
    public void view() throws AccessDeniedException {
//        String message = LogFormatter.addTimestamp(userName + " viewed " );
//        System.out.println(message);

        System.out.println(LogFormatter.addTimestamp(userName + " is viewing document"));
        document.view();
    }

    @Override
    public void edit(String content) throws AccessDeniedException {
        System.out.println(LogFormatter.addTimestamp(userName + " is editing document"));
        document.edit(content);  // ✅ Delegate
    }

    @Override
    public void delete() throws AccessDeniedException {
        System.out.println(LogFormatter.addTimestamp(userName + " is deleting document"));
        document.delete();  // ✅ Delegate


    }

    @Override
    public String getMetadata() throws AccessDeniedException {
        System.out.println(LogFormatter.addTimestamp(userName + " is checking metadata"));
        return document.getMetadata();  // ✅ Delegate and return
    }
}
