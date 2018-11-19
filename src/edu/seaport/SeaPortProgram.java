package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * File SeaPortProgram.java
 * The SeaPortProgram implements an application that displays a GUI, allows a user to import a world file and
 * the user to scan said world file.
 * @author Lance Gundersen
 * @version 2.0
 * @since 2018-11-18
 *
 */

class SeaPortProgram extends JFrame {

    private static final long serialVersionUID = 1L;
    private World world;
    private JButton fileReadButton;
    private JButton searchButton, sortButton;
    private JTextArea textOutput, resultsOutput;
    private TextField searchBox;
    private JComboBox<String> searchDropdown, sortDropdown;

    /**
     * Default Constructor which creates the GUI along with providing click handlers for reading in a file and
     * initializing world searching.
     * @return Nothing.
     */
    private SeaPortProgram() {
        this.createGui();
        this.fileReadButton.addActionListener(e -> scanFile());
        this.searchButton.addActionListener(e -> searchBuilder(searchBox.getText().trim(), searchDropdown.getSelectedIndex()));
        this.sortButton.addActionListener(e -> sortBuilder());
    }

    /**
     * This method sorts the world basedon the sort dropdown choice.
     * @return Nothing.
     */
    private void sortBuilder() {
        // Check if world has data.
        if (this.world != null) {

            if (this.resultsOutput != null)
                this.resultsOutput.setText("");

            switch(Objects.requireNonNull(this.sortDropdown.getSelectedItem()).toString()) {
                case "Weight":
                    this.world.sortByWeight();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                case "Length":
                    this.world.sortByLength();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                case "Draft":
                    this.world.sortByDraft();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                case "Width":
                    this.world.sortByWidth();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                case "Name":
                    this.world.sortByName();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                case "Skill":
                    this.world.sortBySkill();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                case "Index":
                    this.world.sortByIndex();
                    this.resultsOutput.setText(this.world.toString());
                    break;
                default:
                    break;
            }
        } else showMessageDialog(null, "Please choose a file!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This is the main method which calls the SeaPortProgram constructor.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args) {
        new SeaPortProgram();
    }

    /**
     * This method handles basic user input validation by ensuring the world isn't null along with handling
     * failed search results gracefully.
     * @param searchText is the search text passed in by the action listener in the constructor.
     *                   Has null checking to catch empty strings.
     * @param dropdownIndex is the number value of the array option in the array list {0 = not used, 1 = name,
     *                      2 = index, 3 = skill}
     * @return Nothing.
     */
    private void searchBuilder(String searchText, int dropdownIndex) {
        // Check if world has data.
        if (this.world != null) {
            // Check if the search box is empty or if the dropdown is unselected.
            if (!searchText.equals("") && dropdownIndex != 0) {
                String results = this.worldSearch(searchText, this.searchDropdown.getSelectedIndex());
                if (results.equals(""))
                    showMessageDialog(null, "Sorry, " + searchText + " not found!", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                else showMessageDialog(null, results, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else
                showMessageDialog(null, "Search text or dropdown selection missing!", "Error", JOptionPane.ERROR_MESSAGE);
        } else showMessageDialog(null, "Please choose a file!", "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * This method searches the world for strings and ints based on the search option type passed via
     * the GUI dropdown. It returns a set(s) of things or people based on search option passed.
     * @param searchText is the search text passed in by the action listener in the constructor.
     *                   Has null checking to catch empty strings.
     * @return String of parsed values related to input searchText.
     */
    private String worldSearch(String searchText, Integer dropdownIndex) {
        StringBuilder results = new StringBuilder();
        Method method = null;
        // If the option selected is 1 or 2 then return name, index and class.
        if (dropdownIndex != 3) try {
            switch (dropdownIndex) {
                case 1:
                    method = Thing.class.getDeclaredMethod("getName");
                    break;
                case 2:
                    method = Thing.class.getDeclaredMethod("getIndex");
                    break;
                default:
                    break;
            }
            for (Thing item : this.world.getWorld()) {
                assert method != null;
                String parameter = method.invoke(item).toString();
                if (parameter.equals(searchText)) {
                    results.append(item.getName());
                    results.append(" ");
                    results.append(item.getIndex());
                    results.append(" (");
                    results.append(item.getClass().getSimpleName());
                    results.append(")\n");
                }
            }
        } catch (
                NoSuchMethodException |
                        SecurityException |
                        IllegalAccessException |
                        IllegalArgumentException |
                        InvocationTargetException ex
        ) {
            showMessageDialog(null, "Error: " + ex, "Program Error", JOptionPane.ERROR_MESSAGE);
        }
        else { // If the option selected is 3 then return name, and index only.
            for (SeaPort port : this.world.getPorts())
                for (Person person : port.getPersons())
                    if (person.getSkill().equals(searchText)) {
                        results.append(person.getName());
                        results.append(" (index #");
                        results.append(person.getIndex());
                        results.append(")\n");
                    }
        }
        return results.toString();
    }

    /**
     * This method allows the user to open and scan and outputs it to the GUI and instantiates a new world.
     * @return Nothing.
     */
    private void scanFile() {
        JFileChooser jFileChooser = new JFileChooser(".");
        int jFileChooserResult = jFileChooser.showOpenDialog(null);
        if (jFileChooserResult == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "No file selected.");
            }
            this.world = new World(scanner);
            this.textOutput.setText(this.world.toString());
        }
    }

    /**
     * This method creates the main GUI for the SeaPort Program. It leverages the BorderLayout.
     * @return Nothing.
     */
    private void createGui() {
        JFrame frame = new JFrame("SeaPort Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Panel initialization and setup
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new GridLayout(1, 5, 5, 5));
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.setPreferredSize(new Dimension(500, 600));

        // Main text output area styling
        this.textOutput = new JTextArea();
        this.textOutput.setEditable(false);
        this.textOutput.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
        this.textOutput.setLineWrap(true);

        // Results text output area styling
        this.resultsOutput = new JTextArea();
        this.resultsOutput.setEditable(false);
        this.resultsOutput.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
        this.resultsOutput.setLineWrap(true);


        this.fileReadButton = new JButton("Select File");

        JLabel searchBoxLabel = new JLabel("Search:", JLabel.RIGHT);
        this.searchBox = new TextField("", 10);
        String[] searchOptions = new String[]{"", "Name", "Index", "Skill"};
        this.searchDropdown = new JComboBox<>(searchOptions);
        this.searchButton = new JButton("Search");

        JLabel sortBoxLabel = new JLabel("Sort by:", JLabel.RIGHT);
        String[] sortTypeComboBoxValues = new String[] {"Name", "Weight", "Length", "Width", "Draft"};
        sortDropdown = new JComboBox<>(sortTypeComboBoxValues);

        this.sortButton = new JButton("Sort");

        JScrollPane scrollPane = new JScrollPane(this.textOutput);
        JScrollPane resultsPane = new JScrollPane(this.resultsOutput);

        // Panel for the top menu bar
        panelTop.add(this.fileReadButton);
        panelTop.add(searchBoxLabel);
        panelTop.add(this.searchBox);
        panelTop.add(this.searchDropdown);
        panelTop.add(this.searchButton);
        panelTop.add(sortBoxLabel);
        panelTop.add(this.sortDropdown);
        panelTop.add(this.sortButton);

        panelBottom.add(scrollPane);
        panelBottom.add(resultsPane);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
