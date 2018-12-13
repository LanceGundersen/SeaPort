package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * File Person.java
 * The person class contains an array of skills and person(s) working status.
 *
 * @author Lance Gundersen
 * @version 3.0
 * @since 2018-12-13
 */
class Person extends Thing {
    private String skill;
    private boolean isWorking;
    private SeaPort parentPort;
    private JLabel statusLabel, personsReadyLabel, personsTotalLabel;

    // TODO: Track workers total vs. being used.

    /**
     * Default Constructor.
     *
     * @param scannerContents is the file contents to be scanned.
     * @return Nothing.
     */
    Person(Scanner scannerContents, HashMap<Integer, SeaPort> portsMap) {
        super(scannerContents);
        if (!scannerContents.hasNext()) this.setSkill("Error");
        this.setSkill(scannerContents.next());
        this.setIsWorking(false);
        this.setParentPort(portsMap.get(this.getParent()));
    }

    /**
     * Creates the panel for monitoring the resources (skilled labor) for various ports.
     *
     * @return rowPanel detailed panel row
     */
    JPanel getPersonAsPanel() {
        String job = this.getSkill().substring(0, 1).toUpperCase() + this.getSkill().substring(1);
        JPanel rowPanel = new JPanel(new GridLayout(6, 1));
        JLabel nameLabel = new JLabel(this.getName(), JLabel.CENTER);
        JLabel skillLabel = new JLabel(job, JLabel.CENTER);
        JLabel portLabel = new JLabel(this.getParentPort().getName(), JLabel.CENTER);
        this.personsReadyLabel = new JLabel("Workers avail: 0", JLabel.CENTER);
        this.personsTotalLabel = new JLabel("Workers total: 0", JLabel.CENTER);
        this.statusLabel = new JLabel("Available", JLabel.CENTER);

        nameLabel.setOpaque(true);
        skillLabel.setOpaque(true);
        portLabel.setOpaque(true);
        this.personsReadyLabel.setOpaque(true);
        this.personsTotalLabel.setOpaque(true);
        this.statusLabel.setOpaque(true);

        nameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        skillLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        portLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.personsReadyLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.personsTotalLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.statusLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        nameLabel.setBackground(Color.WHITE);
        skillLabel.setBackground(Color.WHITE);
        portLabel.setBackground(Color.WHITE);
        this.personsReadyLabel.setBackground(Color.WHITE);
        this.personsTotalLabel.setBackground(Color.WHITE);
        this.statusLabel.setBackground(Color.decode("#2EC4B6"));

        rowPanel.add(nameLabel);
        rowPanel.add(skillLabel);
        rowPanel.add(portLabel);
        rowPanel.add(this.personsReadyLabel);
        rowPanel.add(this.personsTotalLabel);
        rowPanel.add(this.statusLabel);

        return rowPanel;
    }

    /**
     * Updates a workers availability.
     *
     * @return none
     */
    void updateWorkAvailability() {
        if (this.getIsWorking()) {
            this.statusLabel.setBackground(Color.decode("#D81159"));
            this.statusLabel.setText("Unavailable");
        } else {
            this.statusLabel.setBackground(Color.decode("#2EC4B6"));
            this.statusLabel.setText("Available");
        }
    }

    /**
     * Returns parent port.
     *
     * @return parentPort
     */
    private SeaPort getParentPort() {
        return this.parentPort;
    }

    /**
     * Sets parent port.
     *
     * @param parentPort parentPort
     */
    private void setParentPort(SeaPort parentPort) {
        this.parentPort = parentPort;
    }

    /**
     * Returns working status.
     *
     * @return boolean
     */
    boolean getIsWorking() {
        return this.isWorking;
    }

    /**
     * Sets working status.
     *
     * @param isWorking isWorking
     */
    void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    /**
     * Returns skill.
     */
    String getSkill() {
        return this.skill;
    }

    /**
     * Sets a skill
     *
     * @param skill skill
     */
    private void setSkill(String skill) {
        this.skill = skill;
    }

    public String toString() {
        return String.format("Person: %s %d %s", getName(), getIndex(), this.skill);
    }

}
