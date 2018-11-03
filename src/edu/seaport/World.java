package edu.seaport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class World extends Thing {

    private ArrayList<Thing> allThings;
    private ArrayList<SeaPort> ports;


    World(Scanner scannerContents) {
        super(scannerContents);
        this.setAllThings(new ArrayList<>());
        this.setPorts(new ArrayList<>());
        this.process(scannerContents);
    }

    private void setAllThings(ArrayList<Thing> allThings) {
        this.allThings = allThings;
    }

    private void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }

    ArrayList<Thing> getAllThings() {
        return this.allThings;
    }

    ArrayList<SeaPort> getPorts() {
        return this.ports;
    }

    private void process(Scanner scannerContents) {

        String lineString;
        Scanner lineContents;

        while (scannerContents.hasNextLine()) {
            lineString = scannerContents.nextLine().trim();

            if (lineString.length() == 0) {
                continue;
            }

            lineContents = new Scanner(lineString);

            if (lineContents.hasNext()) {

                switch(lineContents.next().trim()) {
                    case "port":
                        SeaPort newSeaPort = new SeaPort(lineContents);
                        this.getAllThings().add(newSeaPort);
                        this.getPorts().add(newSeaPort);
                        break;
                    case "dock":
                        Dock newDock = new Dock(lineContents);
                        this.getAllThings().add(newDock);
                        this.addThingToList(newDock, "getDocks");
                        break;
                    case "pship":
                        PassengerShip newPassengerShip = new PassengerShip(lineContents);
                        this.getAllThings().add(newPassengerShip);
                        this.addShipToParent(newPassengerShip);
                        break;
                    case "cship":
                        CargoShip newCargoShip = new CargoShip(lineContents);
                        this.getAllThings().add(newCargoShip);
                        this.addShipToParent(newCargoShip);
                        break;
                    case "person":
                        Person newPerson = new Person(lineContents);
                        this.getAllThings().add(newPerson);
                        this.addThingToList(newPerson, "getPersons");
                        break;
                    case "job":
                        Job newJob = new Job(lineContents);
                        this.getAllThings().add(newJob);
                        this.addJobToShip(newJob);
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
            System.out.println("Error: " + ex);
        }
        return null;
    }

    private <T extends Thing> void addThingToList(T newThing, String methodName) {

        SeaPort newPort;
        ArrayList<T> thingsList;
        Method getList;

        newPort = this.getImmediateParentByIndex(this.getPorts(), newThing.getParent());

        try {
            getList = SeaPort.class.getDeclaredMethod(methodName);

            thingsList = (ArrayList<T>) getList.invoke(newPort);
            if (newPort != null) {
                thingsList.add(newThing);
            }
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

    private void addJobToShip(Job newJob) {
        Dock newDock;
        Ship newShip = this.getThingByIndex(newJob.getParent(), "getShips");

        if (newShip != null) {
            newShip.getJobs().add(newJob);
        } else {
            newDock = this.getThingByIndex(newJob.getParent(), "getDocks");
            if (newDock != null) {
                newDock.getShip().getJobs().add(newJob);
            }
        }
    }

    private void addShipToParent(Ship newShip) {
        SeaPort myPort;
        Dock myDock = this.getThingByIndex(newShip.getParent(), "getDocks");

        if (myDock == null) {
            myPort = this.getImmediateParentByIndex(this.getPorts(), newShip.getParent());
            if (myPort != null) {
                myPort.getShips().add(newShip);
            }
            if (myPort != null) {
                myPort.getQue().add(newShip);
            }
        } else {
            myPort = this.getImmediateParentByIndex(this.getPorts(), myDock.getParent());
            myDock.setShip(newShip);
            if (myPort != null) {
                myPort.getShips().add(newShip);
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (SeaPort s : ports) {
            str.append(s.toString()).append("\n");
        }
        return str.toString();
    }
}
