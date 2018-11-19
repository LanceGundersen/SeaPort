package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File SeaPort.java
 * The seaport class contains arrays for docks, queue, ships and persons. As the project progresses the class
 * will be added to.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 */
class SeaPort extends Thing {

    private ArrayList<Dock> docks;
    private ArrayList<Ship> queue;
    private ArrayList<Ship> ships;
    private ArrayList<Person> persons;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    SeaPort(Scanner scannerContents) {
        super(scannerContents);
        this.setDocks(new ArrayList<>());
        this.setQueue(new ArrayList<>());
        this.setShips(new ArrayList<>());
        this.setPersons(new ArrayList<>());
    }

    ArrayList<Dock> getDocks() {
        return this.docks;
    }

    /**
     * Return all jobs list.
     */
    private void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    ArrayList<Ship> getShips() {
        return this.ships;
    }

    /**
     * Return all jobs list.
     */
    private void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    ArrayList<Ship> getQueue() {
        return this.queue;
    }

    /**
     * Return all jobs list.
     */
    private void setQueue(ArrayList<Ship> queue) {
        this.queue = queue;
    }

    ArrayList<Person> getPersons() {
        return this.persons;
    }

    /**
     * Return all persons list.
     */
    private void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n\nSeaPort: " + super.toString());
        for (Dock dock : this.docks) stringBuilder.append("\n").append(dock);
        stringBuilder.append("\n\n --- List of all ships in queue:");
        for (Ship ship : this.queue) stringBuilder.append("\n   > ").append(ship);
        stringBuilder.append("\n\n --- List of all ships:");
        for (Ship ship : this.ships) stringBuilder.append("\n   > ").append(ship);
        stringBuilder.append("\n\n --- List of all persons:");
        for (Person person : this.persons) stringBuilder.append("\n   > ").append(person);
        return stringBuilder.toString();
    }

}
