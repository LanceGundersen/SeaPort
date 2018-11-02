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

    void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    ArrayList<Ship> getShips() {
        return ships;
    }

    void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    ArrayList<Ship> getQue() {
        return que;
    }

    void setQue(ArrayList<Ship> que) {
        this.que = que;
    }

    ArrayList<Person> getPersons() {
        return persons;
    }

    void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
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
