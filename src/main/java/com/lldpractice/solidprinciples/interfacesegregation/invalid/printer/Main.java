package com.lldpractice.solidprinciples.interfacesegregation.invalid.printer;

public class Main {
    public static void main(String[] args) {
        Machine printer = new OldPrinter();
        printer.print("Resume.pdf");  // ✅ Works fine
        printer.scan("Resume.pdf");   // ❌ Throws UnsupportedOperationException at runtime!
        printer.fax("Resume.pdf");    // ❌ Throws UnsupportedOperationException at runtime!
    }
}

/*
 * What's wrong?
 * OldPrinter is forced to implement scan(), fax(), staple() — capabilities it doesn't have.
 * This also violates LSP: substituting OldPrinter for Machine breaks the contract.
 * Every time Machine grows (e.g., add copy()), OldPrinter must be updated again.
 *
 * Root cause: One interface is trying to represent ALL possible machine capabilities.
 */

