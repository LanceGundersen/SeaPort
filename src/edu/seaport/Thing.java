package edu.seaport;

import java.util.Comparator;
import java.util.Scanner;

/**
 * File Thing.java
 * The thing class. As the project progresses the class will be added to.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 */
class Thing implements Comparable<Thing>, Comparator<Thing> {
    private int index;
    private String name;
    private int parent;

    /**
     * Default Constructor which creates the world.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Thing(Scanner scannerContents) {
        if (scannerContents.hasNext()) {
            this.setName(scannerContents.next());
        }
        if (scannerContents.hasNextInt()) {
            this.setIndex(scannerContents.nextInt());
        }
        if (scannerContents.hasNextInt()) {
            this.setParent(scannerContents.nextInt());
        }
    }

    /**
     * Get index.
     */
    int getIndex() {
        return this.index;
    }

    /**
     * Set index.
     */
    private void setIndex(int index) {
        this.index = index;
    }

    /**
     * Get name.
     */
    String getName() {
        return this.name;
    }

    /**
     * Set name.
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Get parent.
     */
    int getParent() {
        return this.parent;
    }

    /**
     * Set parent.
     */
    private void setParent(int parent) {
        this.parent = parent;
    }

    public int compareTo(Thing thingInstance) {
        if (thingInstance.getName().equals(this.getName())) {
            if ((thingInstance.getIndex() == this.getIndex()) && (thingInstance.getParent() == this.getParent()))
                return 1;
            return 0;
        }
        return 0;
    }

    public int compare(Thing thing1, Thing thing2) {

        switch (this.name) {
            case "Weight":
                return Double.compare(((Ship) thing1).getWeight(), ((Ship) thing2).getWeight());
            case "Length":
                return Double.compare(((Ship) thing1).getLength(), ((Ship) thing2).getLength());
            case "Draft":
                return Double.compare(((Ship) thing1).getDraft(), ((Ship) thing2).getDraft());
            case "Width":
                return Double.compare(((Ship) thing1).getWidth(), ((Ship) thing2).getWidth());
            case "Name":
                return thing1.getName().compareTo(thing2.getName());
            default:
                return 0;
        }
    }

    public String toString() {
        return this.name + " " + this.index;
    }
}
