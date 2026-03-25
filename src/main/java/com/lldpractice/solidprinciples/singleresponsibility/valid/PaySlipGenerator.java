package com.lldpractice.solidprinciples.singleresponsibility.valid;

// Responsibility 4: Only handles report generation
class PaySlipGenerator {
    private final SalaryCalculator calculator;

    public PaySlipGenerator(SalaryCalculator calculator) {
        this.calculator = calculator;
    }

    public String generate(Employee employee) {
        double bonus = calculator.calculateBonus(employee);
        return "PaySlip for " + employee.getName() +
                ": Salary=" + employee.getSalary() +
                ", Bonus=" + bonus;
    }
}