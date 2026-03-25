package com.lldpractice.solidprinciples.interfacesegregation.valid.worker;

// ✅ Robot — only works. No eat, sleep, meeting, report. Clean and honest!
class RobotWorker implements Workable {
    @Override public void work() { System.out.println("Robot working 24/7"); }
    // No other methods — not forced to implement irrelevant things ✅
}

