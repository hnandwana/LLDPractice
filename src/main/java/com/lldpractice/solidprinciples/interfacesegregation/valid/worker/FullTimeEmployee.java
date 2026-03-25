package com.lldpractice.solidprinciples.interfacesegregation.valid.worker;

// ✅ Full-time employee — implements ALL relevant interfaces, every method is meaningful
class FullTimeEmployee implements Workable, Eatable, Sleepable, Meetable, Reportable {
    @Override public void work()          { System.out.println("Working full-time"); }
    @Override public void eat()           { System.out.println("Eating lunch"); }
    @Override public void sleep()         { System.out.println("Going home to sleep"); }
    @Override public void attendMeeting() { System.out.println("In a meeting"); }
    @Override public void writeReport()   { System.out.println("Writing report"); }
}

