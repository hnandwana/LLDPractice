package com.lldpractice.designpatterns.structural.proxy.proxies;

import com.lldpractice.designpatterns.structural.proxy.document.Document;
import lombok.AllArgsConstructor;

import java.nio.file.AccessDeniedException;

@AllArgsConstructor
public class ProtectionProxy implements Document {
    private Document document;
    private String role;

    @Override
    public void view() throws AccessDeniedException {
        document.view();
    }

    @Override
    public void edit(String content) throws AccessDeniedException {
        if (role.equals("ADMIN") || role.equals("EDITOR")) {
            document.edit("Edited content");
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @Override
    public void delete() throws AccessDeniedException {
        if (role.equals("ADMIN")) {
            document.delete();
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @Override
    public String getMetadata() throws AccessDeniedException {

        return document.getMetadata();

    }
}
