package com.lldpractice.solidprinciples.interfacesegregation.invalid.printer;

// ❌ Old basic printer — can ONLY print
// Forced to implement scan, fax, staple which it doesn't support
class OldPrinter implements Machine {
    @Override
    public void print(String doc) {
        System.out.println("Printing: " + doc); // ✅ This is fine
    }

    @Override
    public void scan(String doc) {
        // ❌ Can't scan! But forced to implement
        throw new UnsupportedOperationException("OldPrinter cannot scan");
    }

    @Override
    public void fax(String doc) {
        // ❌ Can't fax!
        throw new UnsupportedOperationException("OldPrinter cannot fax");
    }

    @Override
    public void staple(String doc) {
        // ❌ Can't staple!
        throw new UnsupportedOperationException("OldPrinter cannot staple");
    }
}

