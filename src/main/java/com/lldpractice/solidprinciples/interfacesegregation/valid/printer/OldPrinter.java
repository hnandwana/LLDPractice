package com.lldpractice.solidprinciples.interfacesegregation.valid.printer;

// ✅ Old printer — only implements Printable. Honest about what it can do.
class OldPrinter implements Printable {
    @Override
    public void print(String doc) {
        System.out.println("Printing: " + doc); // ✅ Only what it supports
    }
    // No scan, fax — NOT forced ✅
}

