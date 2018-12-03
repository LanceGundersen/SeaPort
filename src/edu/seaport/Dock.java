package edu.seaport;

import java.util.Scanner;

/**
 * File Dock.java
 * The dock class contains dock objects.
 *
 * @author Lance Gundersen
 * @version 1.1
 * @since 2018-12-02
 */
class Dock extends Thing {

    private Ship ship;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Dock(Scanner scannerContents) {
        super(scannerContents);
    }

    /**
     * Return all ship.
     */
    Ship getShip() {
        return this.ship;
    }

    /**
     * Set ship.
     * @param ship Ship
     */
    void setShip(Ship ship) {
        this.ship = ship;
    }

    public String toString() {
        return String.format("Dock: %s\n  Ship: %s", super.toString(), this.ship.toString());
    }

}
