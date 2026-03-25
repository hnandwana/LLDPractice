package com.lldpractice.solidprinciples.interfacesegregation.valid.printer;

// Client depends ONLY on what it needs — not the whole fat Machine interface
class PrintClient {
    private final Printable printer;

    public PrintClient(Printable printer) {
        this.printer = printer;
    }

    public void printDocument(String doc) {
        printer.print(doc); // Works with OldPrinter OR MultiFunctionPrinter ✅
    }
}

