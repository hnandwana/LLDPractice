package com.lldpractice.solidprinciples.singleresponsibility.valid;

// Responsibility 2: Only handles salary calculations
public class SalaryCalculator {

    public double calculateBonus(Employee employee) {
        return employee.getSalary() * 0.1;
    }

    public double calculateTax(Employee employee) {
        return employee.getSalary() * 0.3;
    }

}