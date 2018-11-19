package edu.seaport;

import java.util.Scanner;

/**
 * File CargoShip.java
 * As the project progresses the class will be added to.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 */
class CargoShip extends Ship {

    private double cargoValue, cargoVolume, cargoWeight;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    CargoShip(Scanner scannerContents) {

        super(scannerContents);

        if (scannerContents.hasNextDouble()) {
            this.setCargoWeight(scannerContents.nextDouble());
            this.setCargoVolume(scannerContents.nextDouble());
            this.setCargoValue(scannerContents.nextDouble());
        }

    }

    private double getCargoWeight() {
        return this.cargoWeight;
    }

    private void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    private double getCargoVolume() {
        return this.cargoVolume;
    }

    private void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    private double getCargoValue() {
        return this.cargoValue;
    }

    private void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    public String toString() {

        return String.format("Cargo Ship: %s\n\tCargo Weight: %s\n\tCargo Volume: %s\n\tCargo Value: %s", super.toString(), this.getCargoWeight(), this.getCargoVolume(), this.getCargoValue());
    }

}
