package com.lldpractice.solidprinciples.interfacesegregation.invalid.worker;

// ✅ Full-time employee — uses ALL methods, no problem here
class FullTimeEmployee implements Worker {
    @Override public void work()          { System.out.println("Working full-time"); }
    @Override public void eat()           { System.out.println("Eating lunch"); }
    @Override public void sleep()         { System.out.println("Going home to sleep"); }
    @Override public void attendMeeting() { System.out.println("In a meeting"); }
    @Override public void writeReport()   { System.out.println("Writing report"); }
}

