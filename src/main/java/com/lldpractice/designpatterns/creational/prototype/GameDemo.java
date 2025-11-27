package com.lldpractice.designpatterns.creational.prototype;

import com.lldpractice.designpatterns.creational.prototype.model.Character;
import com.lldpractice.designpatterns.creational.prototype.model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GameDemo {

    private static final Logger log = LoggerFactory.getLogger(GameDemo.class);

    public static void main(String args[]) {

        Character warriorTemplate = Character.createWarriorTemplate();
        Character mageTemplate = Character.createMageTemplate();
        Character archerTemplate = Character.createArcherTemplate();

        // Creating new characters by cloning the templates
        Character warrior1 = warriorTemplate.clone();
        warrior1.setName("Warrior One");
        warrior1.setLevel(2);
        warrior1.getWeapon().setDamage(1000);


        Character mage1 = mageTemplate.clone();
        mage1.setName("Mage One");
        mage1.setLevel(10);
        mage1.getWeapon().setDamage(800);


        Character archer1 = archerTemplate.clone();
        archer1.setName("Archer One");
        archer1.setLevel(7);
        archer1.getWeapon().setDamage(500);

        //Testing the cloned characters and also the templates
        log.info("warriorTemplate: {}", warriorTemplate);
        log.info("warrior1: {}", warrior1);
        log.info("mageTemplate: {}", mageTemplate);
        log.info("mage1: {}", mage1);
        log.info("archerTemplate: {}", archerTemplate);
        log.info("archer1: {}", archer1);


    }
}
