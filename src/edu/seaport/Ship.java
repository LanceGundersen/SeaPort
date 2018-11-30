package edu.seaport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * File Ship.java
 * The ship class contains an array of jobs and sets ship draft, length, weight and width in the constructor.
 *
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-11-19
 */
class Ship extends Thing {
    private double draft, length, weight, width;
    private ArrayList<Job> jobs;

    private SeaPort port;
    private Dock dock;
    private HashMap<Integer, Dock> docksMap;


    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Ship(Scanner scannerContents, HashMap<Integer, Dock> docksMap, HashMap<Integer, SeaPort> portsMap) {
        super(scannerContents);
        if (scannerContents.hasNextDouble()) this.setWeight(scannerContents.nextDouble());
        if (scannerContents.hasNextDouble()) this.setLength(scannerContents.nextDouble());
        if (scannerContents.hasNextDouble()) this.setWidth(scannerContents.nextDouble());
        if (scannerContents.hasNextDouble()) this.setDraft(scannerContents.nextDouble());
        this.setJobs(new ArrayList<>());
        this.setPort(docksMap, portsMap);
        this.setDocksMap(docksMap);
        this.setDock();
    }


    private void setPort(HashMap<Integer, Dock> docksMap, HashMap<Integer, SeaPort> portsMap) {
        this.port = portsMap.get(this.getParent());
        if (this.port == null) {
            Dock pier = docksMap.get(this.getParent());
            this.port = portsMap.get(pier.getParent());
        }
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

    private void setDock() {
        this.dock = this.getDocksMap().getOrDefault(this.getParent(), null);
    }

    Dock getDock() {
        return this.dock;
    }

    void setDock(Dock dock) {
        this.dock = dock;
    }

    SeaPort getPort() {
        return this.port;
    }

    private HashMap<Integer, Dock> getDocksMap() {
        return this.docksMap;
    }

    private void setDocksMap(HashMap<Integer, Dock> docksMap) {
        this.docksMap = docksMap;
    }

    public String toString() {
        StringBuilder stringOutput = new StringBuilder(super.toString() + "\n\tWeight: " + this.getWeight() + "\n\tLength: "
                + this.getLength() + "\n\tWidth: " + this.getWidth() + "\n\tDraft: " + this.getDraft()
                + "\n\tJobs:");

        if (this.getJobs().isEmpty()) {
            stringOutput.append(" None");
        } else {
            for (Job newJob : this.getJobs()) {
                stringOutput.append("\n").append(newJob.toString());
            }
        }

        return stringOutput.toString();
    }

}
