package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File SeaPort.java
 * The seaport class contains arrays for docks, queue, ships and persons and provides synchronized threads for workers.
 *
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-12-02
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

    /**
     * This is a synchronized method for getting person resources.
     *
     * @param job input
     * @return ArrayList of persons
     */
    synchronized ArrayList<Person> getResources(Job job) {
        int counter;
        ArrayList<Person> candidates;
        boolean areAllRequirementsMet;
        counter = this.checkForQualifiedWorkers(job.getRequirements());
        candidates = new ArrayList<>();
        areAllRequirementsMet = true;
        if (counter < job.getRequirements().size()) return new ArrayList<>();
        outerLoop:
        for (String requirement : job.getRequirements()) {
            for (Person person : this.getPersons()) {
                if (person.getSkill().equals(requirement) && !person.getIsWorking()) {
                    person.setIsWorking(true);
                    candidates.add(person);
                    continue outerLoop;
                }
            }
            areAllRequirementsMet = false;
            break;
        }

        if (areAllRequirementsMet) {
            return candidates;
        } else {
            this.returnResources(candidates);
            return null;
        }
    }

    /**
     * This method returns workers to their port.
     *
     * @param resources ArrayList of persons of previous jobs
     * @return nothing
     */
    synchronized void returnResources(ArrayList<Person> resources) {
        resources.forEach((worker) -> worker.setIsWorking(false));
    }

    /**
     * This method returns the number of workers in the current SeaPort with the skills required.
     *
     * @param requirements ArrayList of job requirements
     * @return int counter
     */
    private synchronized int checkForQualifiedWorkers(ArrayList<String> requirements) {
        int counter = 0;
        outerLoop:
        for (String requirement : requirements) {
            for (Person worker : this.getPersons()) {
                if (requirement.equals(worker.getSkill())) {
                    counter++;
                    continue outerLoop;
                }
            }
        }
        return counter;
    }

    /**
     * Return all docks list.
     */
    ArrayList<Dock> getDocks() {
        return this.docks;
    }

    /**
     * Sets a list of Docks.
     * @param docks
     */
    private void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    /**
     * Return all ships list.
     */
    ArrayList<Ship> getShips() {
        return this.ships;
    }

    /**
     * Sets a list of ships
     * @param ships
     */
    private void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    /**
     * Return all queue list.
     */
    ArrayList<Ship> getQueue() {
        return this.queue;
    }

    /**
     * Sets a queue list
     * @param queue
     */
    private void setQueue(ArrayList<Ship> queue) {
        this.queue = queue;
    }

    /**
     * Return all persons list.
     */
    ArrayList<Person> getPersons() {
        return this.persons;
    }

    /**
     * Sets a list of persons
     * @param persons
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
