package edu.seaport;

import java.util.HashMap;
import java.util.Scanner;

/**
 * File CargoShip.java
 * The cargoShip class contains an number of the cargo value, volume and weight for a given ship.
 *
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-12-02
 */
class CargoShip extends Ship {

    private double cargoValue, cargoVolume, cargoWeight;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @param docksMap docks hashmap
     * @param portsMap ports hashmap
     * @return Nothing.
     */
    CargoShip(Scanner scannerContents, HashMap<Integer, Dock> docksMap, HashMap<Integer, SeaPort> portsMap) {
        super(scannerContents, docksMap, portsMap);
        if (scannerContents.hasNextDouble()) this.setCargoWeight(scannerContents.nextDouble());
        if (scannerContents.hasNextDouble()) this.setCargoVolume(scannerContents.nextDouble());
        if (scannerContents.hasNextDouble()) this.setCargoValue(scannerContents.nextDouble());
    }

    /**
     * Return cargo weight.
     */
    private double getCargoWeight() {
        return this.cargoWeight;
    }

    /**
     * Set cargo weight.
     * @param cargoWeight double
     */
    private void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    /**
     * Return cargo volume.
     */
    private double getCargoVolume() {
        return this.cargoVolume;
    }

    /**
     * Set cargo volume.
     * @param cargoVolume double
     */
    private void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    /**
     * Return cargo value.
     */
    private double getCargoValue() {
        return this.cargoValue;
    }

    /**
     * Set cargo value.
     * @param cargoValue double
     */
    private void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    public String toString() {

        return String.format("Cargo Ship: %s\n\tCargo Weight: %s\n\tCargo Volume: %s\n\tCargo Value: %s", super.toString(), this.getCargoWeight(), this.getCargoVolume(), this.getCargoValue());
    }

}
