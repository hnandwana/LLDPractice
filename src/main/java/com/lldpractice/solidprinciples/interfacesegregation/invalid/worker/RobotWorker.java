package com.lldpractice.solidprinciples.interfacesegregation.invalid.worker;

// ❌ Robot implements Worker — but FORCED to implement eat/sleep/meeting/report
// Robots don't do any of these! Classic ISP violation.
class RobotWorker implements Worker {
    @Override public void work() { System.out.println("Robot working 24/7"); }

    @Override public void eat() {
        // ❌ Robots don't eat! Forced to implement this
        throw new UnsupportedOperationException("Robots don't eat");
    }

    @Override public void sleep() {
        // ❌ Robots don't sleep!
        throw new UnsupportedOperationException("Robots don't sleep");
    }

    @Override public void attendMeeting() {
        // ❌ Robots don't attend meetings!
        throw new UnsupportedOperationException("Robots don't attend meetings");
    }

    @Override public void writeReport() {
        // ❌ Not applicable
        throw new UnsupportedOperationException("Robots don't write reports");
    }
}

