package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File Ship.java
 * The ship class contains an array of jobs. As the project progresses the class will be added to.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */
class Ship extends Thing {

    private ArrayList<Job> jobs;

    /**
     * Default Constructor.
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Ship(Scanner scannerContents) {
        super(scannerContents);
        this.jobs = new ArrayList<>();
    }

    /** Return all jobs list. */
    ArrayList<Job> getJobs() {
        return this.jobs;
    }

}
