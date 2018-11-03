package edu.seaport;

import java.util.Scanner;

public class Person extends Thing {

   private String skill;

    Person(Scanner sc) {
        super(sc);
        skill = sc.next();
    }

    private void setSkill(String skill) {
        this.skill = skill;
    }

    String getSkill() {
        return this.skill;
    }

    @Override
    public String toString() {
        return "Person: " + getName() + " " + getIndex() + " " + skill;
    }

}
