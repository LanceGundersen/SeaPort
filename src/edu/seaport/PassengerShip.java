package edu.seaport;

import java.util.Scanner;

public class PassengerShip extends Ship {

    private int numberOfOccupiedRooms;
    private int numberOfPassengers;
    private int numberOfRooms;

    PassengerShip (Scanner sc) {
        super (sc);
        if (sc.hasNextInt()) numberOfPassengers = sc.nextInt();
        if (sc.hasNextInt()) numberOfRooms = sc.nextInt();
        if (sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
    }

    public String toString () {
        StringBuilder st = new StringBuilder("Passenger ship: " + super.toString());
        if (getJobs().size() == 0)
            return st.toString();
        for (Job mj: getJobs()) st.append("\n       - ").append(mj);
        return st.toString();
    }

}
