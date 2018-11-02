package edu.seaport;

import java.util.Scanner;

public class Dock extends Thing {

    private Ship ship;

    Dock(Scanner sc) {
        super(sc);
    }

    Ship getShip() {
        return ship;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }

    public String toString() {
        return "Dock: " + super.toString() + "\n  Ship: " + ship.toString();
    }

}
