package com.lldpractice.designpatterns.creational.prototype.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Character {
    private String name;
    private int level;
    private Weapon weapon;
    private Armor armor;
    private CharacterType characterType;
    private List<Skill> skills;

    // copy constructor
    private Character(Character character) {
        this.name = character.name;
        this.level = character.level;
        this.weapon = character.weapon.clone();
        this.armor = character.armor.clone();
        this.characterType = character.characterType;
        this.skills = List.copyOf(character.skills);
    }

    public Character clone() {
        return new Character(this);
    }

    public static Character createWarriorTemplate() {
        return new Character(
                "Warrior",
                1,
                new Weapon("sword", 90, 50),
                new Armor("heavy armor", 80, 70),
                CharacterType.WARRIOR,
                List.copyOf(List.of(new Skill("high health")))
        );
    }

    public static Character createMageTemplate() {
        return new Character(
                "Mage",
                1,
                new Weapon("staff", 70, 30),
                new Armor("robe", 40, 60),
                CharacterType.MAGE,
                List.copyOf(List.of(new Skill("high mana")))
        );
    }

    public static Character createArcherTemplate() {
        return new Character(
                "Archer",
                1,
                new Weapon("bow", 80, 40),
                new Armor("light armor", 60, 50),
                CharacterType.ARCHER,
                List.copyOf(List.of(new Skill("high agility")))
        );
    }


}
