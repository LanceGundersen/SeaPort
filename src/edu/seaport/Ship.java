package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

public class Ship extends Thing {

    private double draft, length, weight, width;
    private ArrayList<Job> jobs;

    Ship(Scanner sc) {
        super(sc);
        weight = sc.nextDouble();
        length = sc.nextDouble();
        width = sc.nextDouble();
        draft = sc.nextDouble();
        jobs = new ArrayList<>();
    }

    ArrayList<Job> getJobs() {
        return jobs;
    }

}
