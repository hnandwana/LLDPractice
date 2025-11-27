package com.lldpractice.designpatterns.creational.prototype.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Armor {
    private String name;
    private int defense;
    private int durability;


    private Armor(Armor armor) {
        this.name = armor.name;
        this.defense = armor.defense;
        this.durability = armor.durability;
    }

    public Armor clone() {
        return new Armor(this);
    }
}
