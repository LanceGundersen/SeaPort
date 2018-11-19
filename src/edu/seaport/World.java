package edu.seaport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * File World.java
 * The world class creates arrays of things and ports as well as provide various sorting methods.
 *
 * @author Lance Gundersen
 * @version 2.1
 * @since 2018-11-18
 */
class World extends Thing {

    private ArrayList<Thing> allThings;
    private ArrayList<SeaPort> ports;

    /**
     * Default Constructor which creates the world.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    World(Scanner scannerContents) {
        super(scannerContents);
        this.setWorld(new ArrayList<>());
        this.setPorts(new ArrayList<>());
        this.process(scannerContents);
    }

    /**
     * This method performs a sorting of worlds ships by weight.
     *
     * @return Nothing.
     */
    void sortByWeight() {
        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort((ship1, ship2) -> (Double.compare(ship1.getWeight(), ship2.getWeight())));
            seaPort.getShips().sort((ship1, ship2) -> (Double.compare(ship1.getWeight(), ship2.getWeight())));
        }
    }


    /**
     * This method performs a sorting of worlds ships by length.
     *
     * @return Nothing.
     */
    void sortByLength() {

        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort((ship1, ship2) -> (Double.compare(ship1.getLength(), ship2.getLength())));
            seaPort.getShips().sort((ship1, ship2) -> (Double.compare(ship1.getLength(), ship2.getLength())));
        }
    }

    /**
     * This method performs a sorting of worlds ships by draft.
     *
     * @return Nothing.
     */
    void sortByDraft() {
        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort((ship1, ship2) -> (Double.compare(ship1.getDraft(), ship2.getDraft())));
            seaPort.getShips().sort((ship1, ship2) -> (Double.compare(ship1.getDraft(), ship2.getDraft())));
        }
    }

    /**
     * This method performs a sorting of worlds ships by draft.
     *
     * @return Nothing.
     */
    void sortByWidth() {
        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort((ship1, ship2) -> (Double.compare(ship1.getWidth(), ship2.getWidth())));
            seaPort.getShips().sort((ship1, ship2) -> (Double.compare(ship1.getWidth(), ship2.getWidth())));
        }
    }

    /**
     * This method performs a sorting of world by name.
     *
     * @return Nothing.
     */
    void sortByName() {

        if (getPorts().size() > 1) {
            getPorts().sort((port1, port2) -> {
                int res = CASE_INSENSITIVE_ORDER.compare(port1.getName(), port2.getName());
                return (res != 0) ? res : port1.getName().compareTo(port2.getName());
            });
        }

        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort((ship1, ship2) -> {
                int res = CASE_INSENSITIVE_ORDER.compare(ship1.getName(), ship2.getName());
                return (res != 0) ? res : ship1.getName().compareTo(ship2.getName());
            });
            seaPort.getShips().sort((ship1, ship2) -> {
                int res = CASE_INSENSITIVE_ORDER.compare(ship1.getName(), ship2.getName());
                return (res != 0) ? res : ship1.getName().compareTo(ship2.getName());
            });
            seaPort.getPersons().sort((person1, person2) -> {
                int res = CASE_INSENSITIVE_ORDER.compare(person1.getName(), person2.getName());
                return (res != 0) ? res : person1.getName().compareTo(person2.getName());
            });
            seaPort.getDocks().sort((dock1, dock2) -> {
                int res = CASE_INSENSITIVE_ORDER.compare(dock1.getName(), dock2.getName());
                return (res != 0) ? res : dock1.getName().compareTo(dock2.getName());
            });
        }
    }

    /**
     * Return all things list.
     */
    ArrayList<Thing> getWorld() {
        return this.allThings;
    }

    /**
     * Set all things list.
     */
    private void setWorld(ArrayList<Thing> allThings) {
        this.allThings = allThings;
    }

    /**
     * Return all ports list.
     */
    ArrayList<SeaPort> getPorts() {
        return this.ports;
    }

    /**
     * Set ports list.
     */
    private void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }

    /**
     * This method handles scanning of a files contents for populating the world.
     *
     * @param scannerContents is file contents passed into the world class.
     * @return Nothing.
     */
    private void process(Scanner scannerContents) {

        HashMap<Integer, SeaPort> portsMap = new HashMap<>();
        HashMap<Integer, Dock> docksMap = new HashMap<>();
        HashMap<Integer, Ship> shipsMap;
        shipsMap = new HashMap<>();

        while (scannerContents.hasNextLine()) {
            String lineString = scannerContents.nextLine().trim();

            if (lineString.length() == 0) continue;

            Scanner lineContents = new Scanner(lineString);

            if (lineContents.hasNext()) {
                switch (lineContents.next().trim()) {
                    case "port":
                        SeaPort newSeaPort = new SeaPort(lineContents);
                        this.getWorld().add(newSeaPort);
                        this.getPorts().add(newSeaPort);
                        portsMap.put(newSeaPort.getIndex(), newSeaPort);
                        break;
                    case "dock":
                        Dock newDock = new Dock(lineContents);
                        this.getWorld().add(newDock);
                        this.addThingToList(portsMap, newDock, "getDocks");
                        docksMap.put(newDock.getIndex(), newDock);
                        break;
                    case "pship":
                        PassengerShip newPassengerShip = new PassengerShip(lineContents);
                        this.getWorld().add(newPassengerShip);
                        this.addShipToParent(newPassengerShip, docksMap, portsMap);
                        shipsMap.put(newPassengerShip.getIndex(), newPassengerShip);
                        break;
                    case "cship":
                        CargoShip newCargoShip = new CargoShip(lineContents);
                        this.getWorld().add(newCargoShip);
                        this.addShipToParent(newCargoShip, docksMap, portsMap);
                        shipsMap.put(newCargoShip.getIndex(), newCargoShip);
                        break;
                    case "person":
                        Person newPerson = new Person(lineContents);
                        this.getWorld().add(newPerson);
                        this.addThingToList(portsMap, newPerson, "getPersons");
                        break;
                    case "job":
                        Job newJob = new Job(lineContents);
                        this.getWorld().add(newJob);
                        this.addJobToShip(newJob, shipsMap, docksMap);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @SuppressWarnings("unchecked") // No other way I have found besides suppressing the warning.
    private <T extends Thing> void addThingToList(HashMap<Integer, SeaPort> portsMap, T newThing, String methodName) {

        SeaPort newPort = portsMap.get(newThing.getParent());

        try {
            Method getList = SeaPort.class.getDeclaredMethod(methodName);
            ArrayList<T> thingsList = (ArrayList<T>) getList.invoke(newPort);
            if (newPort != null) thingsList.add(newThing);
        } catch (
                NoSuchMethodException |
                        SecurityException |
                        IllegalAccessException |
                        IllegalArgumentException |
                        InvocationTargetException ex
        ) {
            System.out.println("Error: " + ex);
        }
    }

    private void addJobToShip(Job newJob, HashMap<Integer, Ship> shipsMap, HashMap<Integer, Dock> docksMap) {

        Ship newShip = shipsMap.get(newJob.getParent());

        if (newShip != null) newShip.getJobs().add(newJob);
        else {
            Dock newDock = docksMap.get(newJob.getParent());
            newDock.getShip().getJobs().add(newJob);
        }
    }

    private void addShipToParent(Ship newShip, HashMap<Integer, Dock> docksMap, HashMap<Integer, SeaPort> portsMap) {

        SeaPort myPort;
        Dock myDock = docksMap.get(newShip.getParent());

        if (myDock == null) {
            myPort = portsMap.get(newShip.getParent());
            myPort.getShips().add(newShip);
            myPort.getQueue().add(newShip);
        } else {
            myPort = portsMap.get(myDock.getParent());
            myDock.setShip(newShip);
            myPort.getShips().add(newShip);
        }
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (SeaPort seaPort : this.ports) {
            stringBuilder.append(seaPort);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
