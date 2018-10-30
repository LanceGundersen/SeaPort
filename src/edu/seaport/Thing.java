package edu.seaport;

public class Thing implements Comparable<Thing> {
    int index;
    String name;
    int Parent;

    public Thing() {
        return;
    }

    @Override
    public int compareTo(Thing t) {
        return 1;
    }
}
