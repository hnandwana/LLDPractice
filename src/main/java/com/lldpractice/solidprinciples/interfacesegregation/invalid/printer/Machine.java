package com.lldpractice.solidprinciples.interfacesegregation.invalid.printer;

// ❌ FAT interface — not every device can do all of these
interface Machine {
    void print(String doc);
    void scan(String doc);
    void fax(String doc);
    void staple(String doc);
}

