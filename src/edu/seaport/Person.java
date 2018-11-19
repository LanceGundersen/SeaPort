package edu.seaport;

import java.util.Scanner;

/**
 * File Person.java
 * The person class contains an array of skills. As the project progresses the class will be added to.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */
class Person extends Thing {

    private String skill;

    /**
     * Default Constructor.
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Person(Scanner scannerContents) {
        super(scannerContents);
        if (!scannerContents.hasNext()) this.setSkill("Error");
        this.setSkill(scannerContents.next());
    }


    private void setSkill(String skill) {
        this.skill = skill;
    }

    /** Return all jobs list. */
    String getSkill() {
        return this.skill;
    }

    public String toString() {
        return String.format("Person: %s %d %s", getName(), getIndex(), this.skill);
    }

}
