package edu.seaport;

import java.util.Scanner;

public class CargoShip extends Ship {

    private double cargoValue;
    private double cargoVolume;
    private double cargoWeight;

    CargoShip(Scanner sc) {
        super(sc);
        cargoWeight = sc.nextDouble();
        cargoVolume = sc.nextDouble();
        cargoValue = sc.nextDouble();
    }

    @Override
    public String toString() {
        return "Cargo Ship: " + getName() + " " + getIndex();
    }

}
