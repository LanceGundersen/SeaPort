package edu.seaport;

import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * File World.java
 * The world class creates arrays of things and ports, provides various sorting methods and support for outputting
 * file trees.
 *
 * @author Lance Gundersen
 * @version 3
 * @since 2018-12-02
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
            seaPort.getQueue().sort(Comparator.comparingDouble(Ship::getWeight));
            seaPort.getShips().sort(Comparator.comparingDouble(Ship::getWeight));
        }
    }


    /**
     * This method performs a sorting of worlds ships by length.
     *
     * @return Nothing.
     */
    void sortByLength() {

        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort(Comparator.comparingDouble(Ship::getLength));
            seaPort.getShips().sort(Comparator.comparingDouble(Ship::getLength));
        }
    }

    /**
     * This method performs a sorting of worlds ships by draft.
     *
     * @return Nothing.
     */
    void sortByDraft() {
        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort(Comparator.comparingDouble(Ship::getDraft));
            seaPort.getShips().sort(Comparator.comparingDouble(Ship::getDraft));
        }
    }

    /**
     * This method performs a sorting of worlds ships by draft.
     *
     * @return Nothing.
     */
    void sortByWidth() {
        for (SeaPort seaPort : ports) {
            seaPort.getQueue().sort(Comparator.comparingDouble(Ship::getWidth));
            seaPort.getShips().sort(Comparator.comparingDouble(Ship::getWidth));
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
     * Set all world list.
     * @param allThings
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
     * @param ports
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
                        PassengerShip newPassengerShip = new PassengerShip(lineContents, docksMap, portsMap);
                        this.getWorld().add(newPassengerShip);
                        this.addShipToParent(newPassengerShip, docksMap, portsMap);
                        shipsMap.put(newPassengerShip.getIndex(), newPassengerShip);
                        break;
                    case "cship":
                        CargoShip newCargoShip = new CargoShip(lineContents, docksMap, portsMap);
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
                        Job newJob = new Job(lineContents, shipsMap);
                        this.getWorld().add(newJob);
                        this.addJobToShip(newJob, shipsMap, docksMap);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * This method adds things to a list.
     *
     * @param portsMap is the ports hashmap
     * @param newThing is a new thing
     * @param methodName is the declared method name
     * @return Nothing.
     */
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

    /**
     * This method checks for moored ships and adds the job to said ship.
     *
     * @param newJob is the new job
     * @param shipsMap is the ships hashmap
     * @param docksMap is the docks hashmap
     * @return Nothing.
     */
    private void addJobToShip(Job newJob, HashMap<Integer, Ship> shipsMap, HashMap<Integer, Dock> docksMap) {

        Ship newShip = shipsMap.get(newJob.getParent());

        if (newShip != null) {
            newShip.getJobs().add(newJob);
        } else {
            Dock newDock = docksMap.get(newJob.getParent());
            newDock.getShip().getJobs().add(newJob);
        }
    }

    /**
     * This method adds a ship to a queue or to a dock code.
     *
     * @param newShip is the new job
     * @param docksMap is the docks hashmap
     * @param portsMap is the ports hashmap
     * @return Nothing.
     */
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

    /**
     * This method returns a tree based on the world.
     *
     * @return DefaultMutableTreeNode.
     */
    @SuppressWarnings("unchecked") // No other way I have found besides suppressing the warning.
    <T extends Thing> DefaultMutableTreeNode toTree() {
        DefaultMutableTreeNode parentNode, childNode;
        Method getList;
        ArrayList<T> thingsList;
        DefaultMutableTreeNode mainNode = new DefaultMutableTreeNode("World");
        HashMap<String, String> classMethodMap = new HashMap<>() {{
            put("Docks", "getDocks");
            put("Ships", "getShips");
            put("Queue", "getQueue");
            put("Persons", "getPersons");
        }};

        for (SeaPort newPort : this.getPorts()) {
            parentNode = new DefaultMutableTreeNode(newPort.getName() + " (" + newPort.getIndex()
                    + ")");
            mainNode.add(parentNode);

            for (HashMap.Entry<String, String> pair : classMethodMap.entrySet()) {
                try {
                    getList = SeaPort.class.getDeclaredMethod(pair.getValue());
                    thingsList = (ArrayList<T>) getList.invoke(newPort);
                    childNode = this.addBranch(pair.getKey(), thingsList);
                    parentNode.add(childNode);
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
        }

        return mainNode;
    }

    /**
     * This method returns a tree branch of each SeaPort.
     *
     * @param title variable to set the title of the branch
     * @param thingsList the contents of an ArrayList
     * @return DefaultMutableTreeNode.
     */
    private <T extends Thing> DefaultMutableTreeNode addBranch(String title, ArrayList<T> thingsList) {
        String newThingName, childTitle;
        DefaultMutableTreeNode childNode;
        Dock thisDock;
        Ship mooredShip, newShip;
        DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(title);

        for (T newThing : thingsList) {
            newThingName = String.format("%s (%d)", newThing.getName(), newThing.getIndex());
            childNode = new DefaultMutableTreeNode(newThingName);

            if (!(newThing instanceof Dock)) {
                if (newThing instanceof Ship) {
                    newShip = (Ship) newThing;
                    if (!newShip.getJobs().isEmpty()) {
                        for (Job newJob : newShip.getJobs()) {
                            childTitle = newJob.getName();
                            childNode.add(new DefaultMutableTreeNode(childTitle));
                        }
                    }
                }
            } else {
                thisDock = (Dock) newThing;
                mooredShip = thisDock.getShip();
                if (thisDock.getShip() != null) {
                    childTitle = String.format("%s (%d)", mooredShip.getName(), mooredShip.getIndex());
                    childNode.add(new DefaultMutableTreeNode(childTitle));
                }
            }
            parentNode.add(childNode);
        }

        return parentNode;
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
