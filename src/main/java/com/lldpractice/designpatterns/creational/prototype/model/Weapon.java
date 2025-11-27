package com.lldpractice.designpatterns.creational.prototype.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weapon {

    private String name;
    private int damage;
    private int durability;


    private Weapon(Weapon weapon) {
        this.name = weapon.name;
        this.damage = weapon.damage;
        this.durability = weapon.durability;
    }

    public Weapon clone() {
        return new Weapon(this);
    }

}
