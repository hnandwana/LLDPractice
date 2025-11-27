package com.lldpractice.designpatterns.structural.composite;

import com.lldpractice.designpatterns.structural.composite.menu.GroupType;
import com.lldpractice.designpatterns.structural.composite.menu.MenuComponent;
import com.lldpractice.designpatterns.structural.composite.menu.MenuGroup;
import com.lldpractice.designpatterns.structural.composite.menu.MenuItem;

public class CompositeMenu {
    public static void main(String[] args) {
        // Step 1: Create individual menu items (leaves)
        MenuComponent paneerTikka = new MenuItem("Paneer Tikka", 180);
        MenuComponent springRoll = new MenuItem("Spring Roll", 120);
        MenuComponent vegManchurian = new MenuItem("Veg Manchurian", 150);
        MenuComponent dalMakhani = new MenuItem("Dal Makhani", 220);
        MenuComponent naan = new MenuItem("Naan", 40);
        MenuComponent coke = new MenuItem("Coke", 50);

        // Step 2: Create a combo meal (composite)
        MenuComponent starterPlatter = new MenuGroup("Starter Platter", GroupType.COMBO);
        starterPlatter.add(springRoll);
        starterPlatter.add(vegManchurian);
        // Starter Platter = 120 + 150 = ₹270

        // Step 3: Create starters category (composite)
        MenuComponent startersCategory = new MenuGroup("Starters", GroupType.CATEGORY);
        startersCategory.add(paneerTikka);
        startersCategory.add(starterPlatter);
        // Starters = 180 + 270 = ₹450

        // Step 4: Create main course category
        MenuComponent mainCourseCategory = new MenuGroup("Main Course", GroupType.CATEGORY);
        mainCourseCategory.add(dalMakhani);
        mainCourseCategory.add(naan);
        // Main Course = 220 + 40 = ₹260

        // Step 5: Create weekend special offer (composite)
        MenuComponent weekendSpecial = new MenuGroup("Weekend Special", GroupType.OFFER);
        weekendSpecial.add(startersCategory);//3
        weekendSpecial.add(mainCourseCategory);//3
        weekendSpecial.add(coke);//1
        // Weekend Special = 450 + 260 + 50 = ₹760

        // Step 6: Display and test
        System.out.println("=== Restaurant Menu ===\n");
        weekendSpecial.display("");

        System.out.println("\n=== Price Breakdown ===");
        System.out.println("Single Item (Paneer Tikka): ₹" + paneerTikka.getPrice());
        System.out.println("Combo (Starter Platter): ₹" + starterPlatter.getPrice());
        System.out.println("Category (Starters): ₹" + startersCategory.getPrice());
        System.out.println("Offer (Weekend Special): ₹" + weekendSpecial.getPrice());

        // Step 7: Test adding/removing dynamically
        System.out.println("\n=== After Adding Extra Item to Combo ===");
        MenuComponent extraItem = new MenuItem("Garlic Bread", 80);
        starterPlatter.add(extraItem);
        weekendSpecial.display("");
        System.out.println("New Offer Price: ₹" + weekendSpecial.getPrice());

        // Step 8: Test exception on leaf node
        System.out.println("\n=== Testing Exception on Leaf ===");
        try {
            paneerTikka.add(coke);  // Should throw exception
        } catch (UnsupportedOperationException e) {
            System.out.println("✅ Exception caught: " + e.getMessage());
        }

        MenuComponent emptyCombo = new MenuGroup("Empty Combo", GroupType.COMBO);
        System.out.println(emptyCombo.getPrice());  // What prints?


    }
}


//        MenuComponent pizza = new MenuItem("Pizza", 8.99);
//        MenuComponent burger = new MenuItem("Burger", 5.99);
//        MenuComponent pasta = new MenuItem("Pasta", 7.99);
//
//        MenuComponent combo1 = new MenuGroup("Combo 1", GroupType.COMBO);
//        combo1.add(pizza);
//        combo1.add(burger);
//        MenuComponent combo2 = new MenuGroup("Combo 2", GroupType.COMBO);
//        combo2.add(burger);
//        combo2.add(pasta);
//
//        MenuComponent combos = new MenuGroup("Offers", GroupType.OFFER);
//        combos.add(combo1);
//        combos.add(combo2);
//
//        MenuComponent lunchMenu = new MenuGroup("Lunch Menu", GroupType.CATEGORY);
//        lunchMenu.add(pizza);
//        lunchMenu.add(burger);
//        lunchMenu.add(combo1);
//
//        MenuComponent dinnerMenu = new MenuGroup("Dinner Menu", GroupType.CATEGORY);
//        dinnerMenu.add(pasta);
//        dinnerMenu.add(combo2);
//
//        MenuComponent fullMenu = new MenuGroup("WEEKEND SPECIAL", GroupType.CATEGORY);
//        fullMenu.add(lunchMenu);
//        fullMenu.add(dinnerMenu);
//        fullMenu.add(combos);
//
//        fullMenu.display("");