package com.lldpractice.solidprinciples.interfacesegregation.valid.worker;

public class Main {
    public static void main(String[] args) {
        // Full-time employee can do everything
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.work();          // ✅
        employee.eat();           // ✅
        employee.attendMeeting(); // ✅
        employee.writeReport();   // ✅

        // Intern can work, eat, sleep — but NOT forced to do reports or meetings
        Intern intern = new Intern();
        intern.work();  // ✅
        intern.eat();   // ✅
        intern.sleep(); // ✅

        // Robot only works — clean, no fake methods
        RobotWorker robot = new RobotWorker();
        robot.work();   // ✅ No exceptions, no surprises

        // Client code depends only on what it needs
        runWork(employee); // ✅
        runWork(intern);   // ✅
        runWork(robot);    // ✅ All three are Workable
    }

    // This client only cares about Workable — works for any worker type
    static void runWork(Workable worker) {
        worker.work();
    }
}

/*
 * Why this works:
 * Each class implements ONLY the interfaces relevant to it — no fake/empty methods.
 * RobotWorker is no longer forced to deal with eat/sleep/meetings.
 * Adding a new interface (e.g., Trainable) doesn't impact existing classes at all.
 * Client code (runWork) depends on Workable — not the fat Worker interface.
 *
 * ISP Fix = Segregate interfaces by capability, let each class pick what it needs.
 */

/*
 * "ISP means don't force classes to implement methods they don't use.
 *  I split fat interfaces into smaller focused ones — like separating Workable,
 *  Eatable, Meetable instead of one giant Worker interface.
 *  This also prevents LSP violations since classes won't need dummy implementations."
 */

