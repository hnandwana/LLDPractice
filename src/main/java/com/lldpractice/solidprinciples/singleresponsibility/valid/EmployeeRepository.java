package com.lldpractice.solidprinciples.singleresponsibility.valid;

// Responsibility 3: Only handles persistence
class EmployeeRepository {
    public void save(Employee employee) {
        // JDBC / JPA code
        System.out.println("Saving " + employee.getName() + " to database");
    }

    public Employee findByName(String name) {
        // Query DB
        return null;
    }
}