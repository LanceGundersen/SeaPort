package edu.seaport;

import java.util.Scanner;

/**
 * File Dock.java
 * The dock class contains dock objects. As the project progresses the class will be added to.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */
class Dock extends Thing {

    private Ship ship;

    /**
     * Default Constructor.
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Dock(Scanner scannerContents) {
        super(scannerContents);
    }

    /** Return all ship. */
    Ship getShip() {
        return this.ship;
    }

    /** Set ship. */
    void setShip(Ship ship) {
        this.ship = ship;
    }

    public String toString() {
        return "Dock: " + super.toString() + "\n  Ship: " + this.ship.toString();
    }

}
