package com.lldpractice.solidprinciples.interfacesegregation.invalid.worker;

// ❌ FAT interface — forces ALL implementors to have ALL methods
// even if they make no sense for them
interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void writeReport();
}

