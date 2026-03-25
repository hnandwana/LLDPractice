package com.lldpractice.solidprinciples.interfacesegregation.valid.worker;

// ✅ Intern — works, eats, sleeps. Doesn't write reports or attend meetings. Honest design!
class Intern implements Workable, Eatable, Sleepable {
    @Override public void work()  { System.out.println("Intern working on tasks"); }
    @Override public void eat()   { System.out.println("Eating at cafeteria"); }
    @Override public void sleep() { System.out.println("Going home to rest"); }
    // No attendMeeting() or writeReport() — NOT FORCED ✅
}

