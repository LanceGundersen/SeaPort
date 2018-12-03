package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * File Job.java
 * The job class contains arrays of requirements and multithreading for handling jobs for ships. Contains methods that
 * support returning job status to the GUI in the format of a panel.
 *
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-12-02
 */
final class Job extends Thing implements Runnable {
    private double duration;
    private ArrayList<String> requirements;
    private Ship parentShip;
    private SeaPort parentPort;
    private boolean isSuspended, isCancelled, isFinished, isEndex;
    private Thread jobThread;
    private ArrayList<Person> workers;
    private JButton suspendButton;
    private JProgressBar jobProgress;

    /**
     * Default Constructor.
     * @param scannerContents is the file contents to be scanned.
     * @param shipsMap ships hashmap
     * @return Nothing.
     */
    Job(Scanner scannerContents, HashMap<Integer, Ship> shipsMap) {
        super(scannerContents);
        if (scannerContents.hasNextDouble()) this.setDuration(scannerContents.nextDouble());
        this.setRequirements(new ArrayList<>());
        while (scannerContents.hasNext()) this.getRequirements().add(scannerContents.next());
        this.setParentShip(shipsMap.get(this.getParent()));
        this.setParentPort(this.getParentShip().getPort());
        this.setWorkers(new ArrayList<>());
        this.setIsSuspended(false);
        this.setIsCancelled(false);
        this.setIsFinished(false);
        this.setJobThread(new Thread(this));
        this.setIsEndex();
    }

    /**
     * Sets endex.
     */
    private void setIsEndex() {
        this.isEndex = false;
    }

    /**
     * Return job duration.
     */
    private double getDuration() {
        return this.duration;
    }

    /**
     * Sets job duration.
     *
     * @param duration double
     */
    private void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Return list of job requirements.
     */
    ArrayList<String> getRequirements() {
        return this.requirements;
    }

    /**
     * Sets requirements list.
     *
     * @param requirements ArrayList
     */
    private void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    /**
     * Returns parent ship.
     */
    private Ship getParentShip() {
        return this.parentShip;
    }

    /**
     * Sets parent ship.
     *
     * @param parentShip Ship
     */
    private void setParentShip(Ship parentShip) {
        this.parentShip = parentShip;
    }

    /**
     * Returns parent port.
     */
    private SeaPort getParentPort() {
        return this.parentPort;
    }

    /**
     * Sets parent port.
     *
     * @param parentPort SeaPort
     */
    private void setParentPort(SeaPort parentPort) {
        this.parentPort = parentPort;
    }

    /**
     * Returns workers list.
     */
    private ArrayList<Person> getWorkers() {
        return this.workers;
    }

    /**
     * Sets workers list.
     *
     * @param workers ArrayList
     */
    private void setWorkers(ArrayList<Person> workers) {
        this.workers = workers;
    }

    /**
     * Returns boolean whether the job is suspended.
     */
    private boolean getIsSuspended() {
        return !this.isSuspended;
    }

    /**
     * Sets job suspension status.
     *
     * @param isSuspended boolean
     */
    private void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    /**
     * Returns boolean whether the job is cancelled.
     */
    private boolean getIsCancelled() {
        return this.isCancelled;
    }

    /**
     * Sets job cancellation status.
     *
     * @param isCancelled boolean
     */
    private void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    /**
     * Returns boolean whether the job is finished.
     */
    private boolean getIsFinished() {
        return this.isFinished;
    }

    /**
     * Sets job finished status.
     *
     * @param isFinished boolean
     */
    private void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * Returns thread for job.
     */
    private Thread getJobThread() {
        return this.jobThread;
    }

    /**
     * Sets job thread status.
     *
     * @param jobThread Thread
     */
    private void setJobThread(Thread jobThread) {
        this.jobThread = jobThread;
    }

    /**
     * Returns boolean whether the job is endex or not.
     */
    private boolean getIsEndex() {
        return this.isEndex;
    }

    JPanel getJobAsPanel() {
        JPanel rowPanel = new JPanel(new GridLayout(1, 4));
        JLabel rowLabel = new JLabel(this.getName() + " (" + this.getParentShip().getName() + ")", JLabel.CENTER);
        rowLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.jobProgress = new JProgressBar();
        this.jobProgress.setStringPainted(true);
        this.jobProgress.setBackground(Color.decode("#F8FAFF"));
        this.jobProgress.setForeground(Color.decode("#fff"));
        this.suspendButton = new JButton("Suspend");
        JButton cancelButton = new JButton("Cancel");
        this.suspendButton.addActionListener((ActionEvent e) -> this.toggleSuspendFlag());
        cancelButton.addActionListener((ActionEvent e) -> this.toggleCancelFlag());
        rowPanel.add(rowLabel);
        rowPanel.add(this.jobProgress);
        rowPanel.add(this.suspendButton);
        rowPanel.add(cancelButton);
        return rowPanel;
    }

    /**
     * Method for kicking off threads
     *
     * @return nothing
     */
    void startJob() {
        this.setIsFinished(false);
        this.getJobThread().start();
    }

    /**
     * Method for toggling suspend boolean via click handler.
     *
     * @return nothing
     */
    private void toggleSuspendFlag() {
        this.setIsSuspended(this.getIsSuspended());
    }

    private void toggleCancelFlag() {
        this.setIsCancelled(true);
        this.setIsFinished(true);
    }

    /**
     * Method for handling GUI color and text based on status.
     *
     * @param status enum value
     * @return nothing
     */
    private void showStatus(Status status) {
        switch (status) {
            case RUNNING:
                this.suspendButton.setBackground(Color.decode("#2EC4B6"));
                this.suspendButton.setText("Running");
                break;
            case SUSPENDED:
                this.suspendButton.setBackground(Color.decode("#D81159"));
                this.suspendButton.setText("Suspended");
                break;
            case WAITING:
                this.suspendButton.setBackground(Color.decode("#FFBC42"));
                this.suspendButton.setText("Waiting");
                break;
            case DONE:
                this.suspendButton.setBackground(Color.decode("#218380"));
                this.suspendButton.setText("Done");
                break;
        }
    }

    /**
     * Method for handling thread notifyAll() calls gracefully handle queue and pier matching for job handling.
     *
     * @return boolean flag
     */
    private synchronized boolean isWaiting() {
        ArrayList<Person> dockWorkers;
        if (this.getParentPort().getQueue().contains(this.getParentShip())) {
            return true;
        } else {
            if (!this.getRequirements().isEmpty()) {
                dockWorkers = this.getParentPort().getResources(this);
                if (dockWorkers == null) {
                    return true;
                } else {
                    this.setWorkers(dockWorkers);
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Overriden method for supporting the runnable interface. Handles dock to ship mapping and main synchroiziation
     * employment for the handling of the ships/jobs.
     *
     * @return nothing
     */
    public void run() {
        ArrayList<Boolean> statusList;
        Ship newShipToMoor;
        Dock parentDock;
        long time = System.currentTimeMillis();
        long startTime = time;
        long stopTime = time + 1000 * (long) this.getDuration();
        double timeNeeded = stopTime - time;

        synchronized (this.getParentPort()) {
            while (this.isWaiting()) {
                try {
                    this.showStatus(Status.WAITING);
                    this.getParentPort().wait();
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e);
                }
            }
        }

        while ((time < stopTime) && !this.getIsCancelled()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error: " + e);
            }

            if (this.getIsSuspended()) {
                this.showStatus(Status.RUNNING);
                time += 100;
                this.jobProgress.setValue((int) (((time - startTime) / timeNeeded) * 100));
            } else {
                this.showStatus(Status.SUSPENDED);
            }
            if (this.getIsEndex()) return;
        }

        if (this.getIsSuspended()) {
            this.jobProgress.setValue(100);
            this.showStatus(Status.DONE);
            this.setIsFinished(true);
        }

        synchronized (this.getParentPort()) {
            if (!this.getRequirements().isEmpty() && !this.getWorkers().isEmpty())
                this.getParentPort().returnResources(this.getWorkers());

            statusList = new ArrayList<>();
            this.getParentShip().getJobs().forEach((job) -> statusList.add(job.getIsFinished()));

            if (!statusList.contains(false)) {
                while (!this.getParentPort().getQueue().isEmpty()) {
                    newShipToMoor = this.getParentPort().getQueue().remove(0);

                    if (!newShipToMoor.getJobs().isEmpty()) {
                        parentDock = this.getParentShip().getDock();
                        parentDock.setShip(newShipToMoor);
                        newShipToMoor.setDock(parentDock);
                        break;
                    }
                }
            }
            this.getParentPort().notifyAll();
        }
    }

    public String toString() {
        StringBuilder stringOutput;
        stringOutput = new StringBuilder("\t\t" + super.toString() + "\n\t\tDuration: " + this.getDuration() + "\n\t\tRequirements:");
        if (this.getRequirements().isEmpty()) {
            stringOutput.append("\n\t\t\t - None");
        } else {
            for (String requiredSkill : this.getRequirements()) {
                stringOutput.append("\n\t\t\t - ").append(requiredSkill);
            }
        }
        return stringOutput.toString();
    }

    private enum Status {RUNNING, SUSPENDED, WAITING, DONE}
}