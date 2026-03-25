package com.lldpractice.solidprinciples.interfacesegregation.valid.printer;

public class Main {
    public static void main(String[] args) {
        // Old printer — only prints, no surprises
        OldPrinter oldPrinter = new OldPrinter();
        oldPrinter.print("Resume.pdf"); // ✅

        // Modern printer — does everything it advertises
        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        mfp.print("Report.pdf"); // ✅
        mfp.scan("Report.pdf");  // ✅
        mfp.fax("Report.pdf");   // ✅

        // PrintClient only depends on Printable — works with BOTH printer types
        PrintClient client1 = new PrintClient(oldPrinter); // ✅
        PrintClient client2 = new PrintClient(mfp);        // ✅

        client1.printDocument("Invoice.pdf"); // ✅ Prints using OldPrinter
        client2.printDocument("Invoice.pdf"); // ✅ Prints using MultiFunctionPrinter
    }
}

/*
 * Why this works:
 * OldPrinter only implements Printable — it's honest about what it can do.
 * MultiFunctionPrinter implements all three — no fake methods anywhere.
 * PrintClient depends on Printable (interface), not on a concrete class.
 * Adding a new capability (e.g., Stapleable) doesn't touch any existing class.
 *
 * ISP Fix = Split interfaces by capability. Each class picks only what it needs.
 */

/*
 * "ISP means don't force classes to implement methods they don't use.
 *  I split fat interfaces into smaller focused ones — like separating Printable,
 *  Scannable, Faxable instead of one giant Machine interface.
 *  This also prevents LSP violations since classes won't need dummy implementations."
 */

