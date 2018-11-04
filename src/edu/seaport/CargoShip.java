package edu.seaport;

import java.util.Scanner;

/**
 * File CargoShip.java
 * As the project progresses the class will be added to.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */
class CargoShip extends Ship {

    /**
     * Default Constructor.
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    CargoShip(Scanner scannerContents) {
        super(scannerContents);
    }

    public String toString() {
        return "Cargo Ship: " + getName() + " " + getIndex();
    }

}
