package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

public class SeaPort extends Thing {

    private ArrayList<Dock> docks;
    private ArrayList<Ship> que;
    private ArrayList<Ship> ships;
    private ArrayList<Person> persons;

    SeaPort(Scanner sc) {
        super(sc);
        docks = new ArrayList<>();
        ships = new ArrayList<>();
        que = new ArrayList<>();
        persons = new ArrayList<>();
    }

    ArrayList<Dock> getDocks() {
        return docks;
    }

    ArrayList<Ship> getShips() {
        return ships;
    }

    ArrayList<Ship> getQue() {
        return que;
    }

    ArrayList<Person> getPersons() {
        return persons;
    }

    public String toString() {
        StringBuilder st = new StringBuilder("\n\nSeaPort: " + super.toString());
        for (Dock md: docks) st.append("\n").append(md);
        st.append("\n\n --- List of all ships in que:");
        for (Ship ms: que ) st.append("\n   > ").append(ms);
        st.append("\n\n --- List of all ships:");
        for (Ship ms: ships) st.append("\n   > ").append(ms);
        st.append("\n\n --- List of all persons:");
        for (Person mp: persons) st.append("\n   > ").append(mp);
        return st.toString();
    }

}
