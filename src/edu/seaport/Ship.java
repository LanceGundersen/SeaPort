package edu.seaport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * File Ship.java
 * The ship class contains an array of jobs and docks and sets ship draft, length, weight and width.
 *
 * @author Lance Gundersen
 * @version 3.0
 * @since 2018-12-02
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

    /**
     * Set this port using world hashmap
     *
     * @param docksMap docks hashmap
     * @param portsMap ports hashmap
     * @return void
     */
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

    /**
     * Set ship weight.
     * @param  jobs ArrayList
     */
    private void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     * Return ship weight.
     */
    double getWeight() {
        return this.weight;
    }

    /**
     * Set ship weight.
     * @param  weight double
     */
    private void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Return ship length.
     */
    double getLength() {
        return this.length;
    }

    /**
     * Set ship length.
     * @param  length double
     */
    private void setLength(double length) {
        this.length = length;
    }

    /**
     * Return ship width.
     */
    double getWidth() {
        return this.width;
    }

    /**
     * Set ship width.
     * @param  width double
     */
    private void setWidth(double width) {
        this.width = width;
    }

    /**
     * Return ship draft.
     */
    double getDraft() {
        return this.draft;
    }

    /**
     * Set draft.
     * @param draft double
     */
    private void setDraft(double draft) {
        this.draft = draft;
    }

    /**
     * Set dock to this dock.
     */
    private void setDock() {
        this.dock = this.getDocksMap().getOrDefault(this.getParent(), null);
    }

    /**
     * Return ship dock.
     */
    Dock getDock() {
        return this.dock;
    }

    /**
     * Set dock.
     * @param dock a Dock
     */
    void setDock(Dock dock) {
        this.dock = dock;
    }

    /**
     * Return ship port.
     */
    SeaPort getPort() {
        return this.port;
    }

    /**
     * Return docks list.
     */
    private HashMap<Integer, Dock> getDocksMap() {
        return this.docksMap;
    }

    /**
     * Set docks list.
     */
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
