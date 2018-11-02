package edu.seaport;

import java.util.Scanner;

public class Thing implements Comparable<Thing> {
    private int index;
    private String name;
    private int parent;

    Thing(Scanner sc) {
        if (sc.hasNext()) {
            this.setName(sc.next());
        }

        if (sc.hasNextInt()) {
            this.setIndex(sc.nextInt());
        }

        if (sc.hasNextInt()) {
            this.setParent(sc.nextInt());
        }
    }

    private void setIndex(int index) {
        this.index = index;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setParent(int parent) {
        this.parent = parent;
    }

    int getIndex() {
        return this.index;
    }

    String getName() {
        return this.name;
    }

    int getParent() {
        return this.parent;
    }

    @Override
    public int compareTo(Thing thingInstance) {
        if (thingInstance.getName().equals(this.getName())) {
            if ((thingInstance.getIndex() == this.getIndex()) && (thingInstance.getParent() == this.getParent())) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public String toString()
    {
        return name+" "+index;
    }
}
