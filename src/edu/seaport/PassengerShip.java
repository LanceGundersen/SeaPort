package edu.seaport;

import java.util.Scanner;

/**
 * File PassengerShip.java
 * As the project progresses the class will be added to.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */
class PassengerShip extends Ship {

    /**
     * Default Constructor.
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    PassengerShip(Scanner scannerContents) {
        super(scannerContents);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Passenger ship: " + super.toString());
        if (getJobs().size() == 0)
            return stringBuilder.toString();
        for (Job job : getJobs()) stringBuilder.append("\n       - ").append(job);
        return stringBuilder.toString();
    }

}
