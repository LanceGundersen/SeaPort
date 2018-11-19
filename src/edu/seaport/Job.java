package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File Job.java
 * As the project progresses the class will be added to.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 */
class Job extends Thing {

    private double duration;
    private ArrayList<String> requirements;

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Job(Scanner scannerContents) {
        super(scannerContents);
        if (scannerContents.hasNextDouble()) {
            this.setDuration(scannerContents.nextDouble());
        }

        this.setRequirements(new ArrayList<>());
        while (scannerContents.hasNext()) {
            this.getRequirements().add(scannerContents.next());
        }
    }

    private double getDuration() {
        return this.duration;
    }

    private void setDuration(double duration) {
        this.duration = duration;
    }

    private ArrayList<String> getRequirements() {
        return this.requirements;
    }

    private void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    public String toString() {

        return String.format("\t\t%s\n\t\tDuration: %s\n\t\tRequirements:", super.toString(), this.getDuration());
    }
}
