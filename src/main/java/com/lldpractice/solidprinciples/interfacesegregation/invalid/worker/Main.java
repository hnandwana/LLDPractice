package com.lldpractice.solidprinciples.interfacesegregation.invalid.worker;

public class Main {
    public static void main(String[] args) {
        Worker human = new FullTimeEmployee();
        human.work();          // ✅ Works fine
        human.eat();           // ✅ Works fine
        human.attendMeeting(); // ✅ Works fine

        Worker robot = new RobotWorker();
        robot.work();          // ✅ Works fine
        robot.eat();           // ❌ Throws UnsupportedOperationException at runtime!
        robot.attendMeeting(); // ❌ Throws UnsupportedOperationException at runtime!
    }
}

/*
 * What's wrong?
 * RobotWorker is FORCED to implement 4 methods it doesn't need.
 * Throws UnsupportedOperationException everywhere → also violates LSP.
 * If we add takeVacation() to Worker, EVERY class must be updated.
 * The interface is too fat — mixing unrelated responsibilities.
 *
 * ISP Violation Smell:
 * - throw new UnsupportedOperationException() in multiple methods
 * - A class that implements an interface but doesn't need most of its methods
 * - Adding one method to the interface forces changes in many unrelated classes
 */

