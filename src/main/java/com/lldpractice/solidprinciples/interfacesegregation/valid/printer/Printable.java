package com.lldpractice.solidprinciples.interfacesegregation.valid.printer;

// ✅ Focused interfaces — split by capability
public interface Printable {
    void print(String doc);
}

