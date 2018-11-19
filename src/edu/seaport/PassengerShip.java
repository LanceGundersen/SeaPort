package edu.seaport;

import java.util.Scanner;

/**
 * File PassengerShip.java
 * As the project progresses the class will be added to.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 */
class PassengerShip extends Ship {

    private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    PassengerShip(Scanner scannerContents) {
        super(scannerContents);
        if (scannerContents.hasNextInt()) {
            this.setNumberOfPassengers(scannerContents.nextInt());
        }

        if (scannerContents.hasNextInt()) {
            this.setNumberOfRooms(scannerContents.nextInt());
        }

        if (scannerContents.hasNextInt()) {
            this.setNumberOfOccupiedRooms(scannerContents.nextInt());
        }
    }

    private int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    private void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    private int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    private void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    private int getNumberOfOccupiedRooms() {
        return this.numberOfOccupiedRooms;
    }

    private void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }

    public String toString() {

        return String.format("Passenger Ship: %s\n\tPassengers: %d\n\tRooms: %d\n\tOccupied Rooms: %d", super.toString(), this.getNumberOfPassengers(), this.getNumberOfRooms(), this.getNumberOfOccupiedRooms());
    }

}
