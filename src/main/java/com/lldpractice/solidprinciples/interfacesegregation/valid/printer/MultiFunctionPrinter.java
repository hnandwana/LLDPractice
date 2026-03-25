package com.lldpractice.solidprinciples.interfacesegregation.valid.printer;

// ✅ Modern all-in-one — implements all capabilities it genuinely supports
class MultiFunctionPrinter implements Printable, Scannable, Faxable {
    @Override
    public void print(String doc) { System.out.println("Printing: " + doc); }

    @Override
    public void scan(String doc) { System.out.println("Scanning: " + doc); }

    @Override
    public void fax(String doc) { System.out.println("Faxing: " + doc); }
}

