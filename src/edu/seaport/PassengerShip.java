package edu.seaport;

import java.util.HashMap;
import java.util.Scanner;

/**
 * File PassengerShip.java
 * The passengerShip class contains an number of rooms (both occupied and total) and number of passengers.
 *
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-12-02
 */
class PassengerShip extends Ship {

    private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @param docksMap hashmap of docks
     * @param portsMap hashmap of ports
     * @return Nothing.
     */
    PassengerShip(Scanner scannerContents, HashMap<Integer, Dock> docksMap,
                  HashMap<Integer, SeaPort> portsMap) {

        super(scannerContents, docksMap, portsMap);

        if (scannerContents.hasNextInt()) this.setNumberOfPassengers(scannerContents.nextInt());

        if (scannerContents.hasNextInt()) this.setNumberOfRooms(scannerContents.nextInt());

        if (scannerContents.hasNextInt()) this.setNumberOfOccupiedRooms(scannerContents.nextInt());
    }

    /**
     * Returns number of passengers.
     * @return int
     */
    private int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    /**
     * Set number of passengers.
     * @param numberOfPassengers int
     */
    private void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Returns number of rooms.
     * @return int
     */
    private int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    /**
     * Set number of rooms.
     * @param numberOfRooms int
     */
    private void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Returns number of occupied rooms.
     * @return int
     */
    private int getNumberOfOccupiedRooms() {
        return this.numberOfOccupiedRooms;
    }

    /**
     * Set number of occupied rooms.
     * @param numberOfOccupiedRooms int
     */
    private void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }

    public String toString() {

        return String.format("Passenger Ship: %s\n\tPassengers: %d\n\tRooms: %d\n\tOccupied Rooms: %d", super.toString(), this.getNumberOfPassengers(), this.getNumberOfRooms(), this.getNumberOfOccupiedRooms());
    }

}
