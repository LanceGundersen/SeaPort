package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        this.setStatus();
        this.setJobThread(new Thread(this));
        this.setIsEndex();
    }

    private void setStatus() {
    }

    private void setIsEndex() {
        this.isEndex = false;
    }

    private double getDuration() {
        return this.duration;
    }

    private void setDuration(double duration) {
        this.duration = duration;
    }

    ArrayList<String> getRequirements() {
        return this.requirements;
    }

    private void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    private Ship getParentShip() {
        return this.parentShip;
    }

    private void setParentShip(Ship parentShip) {
        this.parentShip = parentShip;
    }

    private SeaPort getParentPort() {
        return this.parentPort;
    }

    private void setParentPort(SeaPort parentPort) {
        this.parentPort = parentPort;
    }

    private ArrayList<Person> getWorkers() {
        return this.workers;
    }

    private void setWorkers(ArrayList<Person> workers) {
        this.workers = workers;
    }

    private boolean getIsSuspended() {
        return !this.isSuspended;
    }

    private void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    private boolean getIsCancelled() {
        return this.isCancelled;
    }

    private void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    private boolean getIsFinished() {
        return this.isFinished;
    }

    private void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    private Thread getJobThread() {
        return this.jobThread;
    }

    private void setJobThread(Thread jobThread) {
        this.jobThread = jobThread;
    }

    private boolean getIsEndex() {
        return this.isEndex;
    }

    JPanel getJobAsPanel() {
        JPanel rowPanel = new JPanel(new GridLayout(1, 4));
        JLabel rowLabel = new JLabel(this.getName() + " (" + this.getParentShip().getName() + ")", JLabel.CENTER);
        rowLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.jobProgress = new JProgressBar();
        this.jobProgress.setStringPainted(true);
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

    void startJob() {
        this.setIsFinished(false);
        this.getJobThread().start();
    }

    private void toggleSuspendFlag() {
        this.setIsSuspended(this.getIsSuspended());
    }

    private void toggleCancelFlag() {
        this.setIsCancelled(true);
        this.setIsFinished(true);
    }

    private void showStatus(Status status) {
        switch (status) {
            case RUNNING:
                this.suspendButton.setBackground(Color.GREEN);
                this.suspendButton.setText("Running");
                break;
            case SUSPENDED:
                this.suspendButton.setBackground(Color.YELLOW);
                this.suspendButton.setText("Suspended");
                break;
            case WAITING:
                this.suspendButton.setBackground(Color.ORANGE);
                this.suspendButton.setText("Waiting");
                break;
            case DONE:
                this.suspendButton.setBackground(Color.RED);
                this.suspendButton.setText("Done");
                break;
        }
    }

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

    public void run() {
        long time, startTime, stopTime;
        double timeNeeded;
        ArrayList<Boolean> statusList;
        Ship newShipToMoor;
        Dock parentDock;
        time = System.currentTimeMillis();
        startTime = time;
        stopTime = time + 1000 * (long) this.getDuration();
        timeNeeded = stopTime - time;

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

        while (time < stopTime && !this.getIsCancelled()) {
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
            if (!this.getRequirements().isEmpty() && !this.getWorkers().isEmpty()) {
                this.getParentPort().returnResources(this.getWorkers());
            }

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