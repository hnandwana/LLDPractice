package com.lldpractice.solidprinciples.singleresponsibility.valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {

    private String name;
    private double salary;


    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }


}
/*"A class should have only one reason to change."*/
/* "SRP means a class should have only one reason to change.
I follow this by splitting concerns into separate classes —
like separating business logic from persistence and notification."*/