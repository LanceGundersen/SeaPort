package edu.seaport;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * File World.java
 * The world class creates arrays of things and ports. As the project progresses the class will be added to.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */
class World extends Thing {

    private ArrayList<Thing> allThings;
    private ArrayList<SeaPort> ports;

    /**
     * Default Constructor which creates the world.
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    World(Scanner scannerContents) {
        super(scannerContents);
        this.setWorld(new ArrayList<>());
        this.setPorts(new ArrayList<>());
        this.process(scannerContents);
    }

    /** Return all things list. */
    ArrayList<Thing> getWorld() {
        return this.allThings;
    }

    /** Set all things list. */
    private void setWorld(ArrayList<Thing> allThings) {
        this.allThings = allThings;
    }

    /** Return all ports list. */
    ArrayList<SeaPort> getPorts() {
        return this.ports;
    }

    /** Set ports list. */
    private void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }

    /**
     * This method handles scanning of a files contents for populating the world.
     * @param scannerContents is file contents passed into the world class.
     * @return Nothing.
     */
    private void process(Scanner scannerContents) {

        HashMap<Integer, SeaPort> portsMap = new HashMap<>();
        HashMap<Integer, Dock> docksMap = new HashMap<>();
        HashMap<Integer, Ship> shipsMap = new HashMap<>();

        while (scannerContents.hasNextLine()) {
            String lineString = scannerContents.nextLine().trim();

            if (lineString.length() == 0) {
                continue;
            }

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

    private <T extends Thing> T getImmediateParentByIndex(ArrayList<T> thingsList, int index) {
        for (T thing : thingsList) {
            if (thing.getIndex() == index) {
                return thing;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked") // No other way I have found besides suppressing the warning.
    private <T extends Thing> T getThingByIndex(int index, String methodName) {

        Method getList;
        T newThing;
        ArrayList<T> thingsList;

        try {
            getList = SeaPort.class.getDeclaredMethod(methodName);
            for (SeaPort port : this.getPorts()) {
                thingsList = (ArrayList<T>) getList.invoke(port);
                newThing = this.getImmediateParentByIndex(thingsList, index);

                if (newThing != null) {
                    return newThing;
                }
            }
        } catch (
                NoSuchMethodException |
                        SecurityException |
                        IllegalAccessException |
                        IllegalArgumentException |
                        InvocationTargetException ex
        ) {
            showMessageDialog(null, "Error: " + ex, "Program Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
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
