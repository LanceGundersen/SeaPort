package edu.seaport;

import java.util.Scanner;

/**
 * File Person.java
 * The person class contains an array of skills and person(s) working status.
 *
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-12-02
 */
class Person extends Thing {

    private String skill;

    private boolean isWorking;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Person(Scanner scannerContents) {
        super(scannerContents);
        if (!scannerContents.hasNext()) this.setSkill("Error");
        this.setSkill(scannerContents.next());

        this.setIsWorking(false);
    }

    /**
     * Returns working status.
     * @return boolean
     */
    boolean getIsWorking() {
        return this.isWorking;
    }

    /**
     * Sets working status.
     * @param isWorking
     */
    void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    /**
     * Returns skill.
     */
    String getSkill() {
        return this.skill;
    }

    /**
     * Sets a skill
     * @param skill
     */
    private void setSkill(String skill) {
        this.skill = skill;
    }

    public String toString() {
        return String.format("Person: %s %d %s", getName(), getIndex(), this.skill);
    }

}
