package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File Ship.java
 * The ship class contains an array of jobs. As the project progresses the class will be added to.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 */
class Ship extends Thing {

    private double draft, length, weight, width;
    private ArrayList<Job> jobs;


    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Ship(Scanner scannerContents) {
        super(scannerContents);
        this.jobs = new ArrayList<>();
    }

    /**
     * Return all jobs list.
     */
    ArrayList<Job> getJobs() {
        return this.jobs;
    }

    private void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    double getWeight() {
        return this.weight;
    }

    private void setWeight(double weight) {
        this.weight = weight;
    }

    double getLength() {
        return this.length;
    }

    private void setLength(double length) {
        this.length = length;
    }

    double getWidth() {
        return this.width;
    }

    private void setWidth(double width) {
        this.width = width;
    }

    double getDraft() {
        return this.draft;
    }

    private void setDraft(double draft) {
        this.draft = draft;
    }

}
