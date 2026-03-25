package com.lldpractice.solidprinciples.singleresponsibility.invalid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    //     Responsibility 1: Employee data management

    private String name;
    private double salary;

    // Responsibility 2: Salary calculation (Business Logic)
    public double calculateBonus() {
        return salary * 0.1;
    }

    // Responsibility 3: Database operations (Persistence)
    public void saveToDatabase() {
        // JDBC code to insert employee into DB
        System.out.println("Saving " + name + " to database...");
    }

    // Responsibility 4: Report generation (Presentation)
    public String generatePaySlip() {
        return "PaySlip for " + name + ": Salary=" + salary + ", Bonus=" + calculateBonus();
    }

    // Responsibility 5: Sending emails (Notification)
    public void sendPaySlipEmail() {
        String paySlip = generatePaySlip();
        // SMTP code to send email
        System.out.println("Emailing payslip to " + name);
    }
    // principle followed or not?
}
