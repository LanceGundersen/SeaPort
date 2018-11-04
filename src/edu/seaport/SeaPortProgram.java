package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * File SeaPortProgram.java
 * The SeaPortProgram implements an application that displays a GUI, allows a user to import a world file and
 * the user to scan said world file.
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-11-03
 *
 */

public class SeaPortProgram extends JFrame {

    private World world;
    private JButton fileReadBtn, searchBtn;
    private JTextArea textOutput;
    private TextField searchBox;
    private JComboBox<String> searchDropdown;

    /**
     * Default Constructor which creates the GUI along with providing click handlers for reading in a file and
     * initializing world searching.
     * @return Nothing.
     */
    private SeaPortProgram() {

        this.createGui();
        this.fileReadBtn.addActionListener(e -> scanFile());
        this.searchBtn.addActionListener(e -> searchBuilder(searchBox.getText().trim(), searchDropdown.getSelectedIndex()));

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

                String results = this.worldSearch(searchText, dropdownIndex);

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
     * @param dropdownIndex is the number value of the array option in the array list {0 = not used, 1 = name,
     *                      2 = index, 3 = skill}
     * @return String of parsed values related to input searchText.
     */
    private String worldSearch(String searchText, int dropdownIndex) {

        StringBuilder results = new StringBuilder();
        Method method = null;

        if (dropdownIndex != 3) { // If the option selected is 1 or 2 then return name, index and class.
            try {
                switch (dropdownIndex) {
                    case 1:
                        method = Thing.class.getDeclaredMethod("getName");
                        break;
                    case 2:
                        method = Thing.class.getDeclaredMethod("getIndex");
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
        } else { // If the option selected is 3 then return name, and index only.

            for (SeaPort port : this.world.getPorts()) {

                for (Person person : port.getPersons()) {

                    if (person.getSkill().equals(searchText)) {

                        results.append(person.getName());
                        results.append(" (index #");
                        results.append(person.getIndex());
                        results.append(")\n");
                    }
                }
            }
        }

        return results.toString();
    }

    /**
     * This method calls getLocalFile method and scans a file defined by the user and outputs it to the GUI and
     * instantiates a new world. This method could use some better error handling when a user doesn't select a
     * file and cancels the FileReader.
     * @return Nothing.
     */
    private void scanFile() {
        try {
            FileReader fileReader = new FileReader(this.getLocalFile());
            Scanner scanner = new Scanner(fileReader);
            this.world = new World(scanner);
            this.textOutput.setText(this.world.toString());
        } catch (IOException e) {
            //TODO: Account for file not being chosen vs. file error.
            System.out.println("ParseFile Exception: " + e);
        }
    }

    /**
     * This method opens a fileChooser GUI. This method could
     * use some better error handling when a user doesn't select a file and cancels the FileReader.
     * @return a file chosen by the user in the current directory path.
     */
    private File getLocalFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.showOpenDialog(null);
        fileChooser.setVisible(true);
        return fileChooser.getSelectedFile();
    }

    /**
     * This method creates the main GUI for the SeaPort Program. It leverages the BorderLayout.
     * @return Nothing.
     */
    private void createGui() {

        JFrame frame = new JFrame("SeaPort Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 600));

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new GridLayout(1, 5, 5, 5));

        this.fileReadBtn = new JButton("Select File");
        this.searchBtn = new JButton("Search");


        JLabel searchBoxLabel = new JLabel("Search:", JLabel.RIGHT);
        this.searchBox = new TextField();
        this.searchBox = new TextField("", 10);

        String[] searchOptions = new String[]{"", "name", "index", "skill"};
        this.searchDropdown = new JComboBox<>(searchOptions);

        // Main text output area styling
        this.textOutput = new JTextArea();
        this.textOutput.setEditable(false);
        this.textOutput.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
        this.textOutput.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textOutput);

        // Panel for the top menu bar
        panelTop.add(this.fileReadBtn);
        panelTop.add(searchBoxLabel);
        panelTop.add(this.searchBox);
        panelTop.add(this.searchDropdown);
        panelTop.add(this.searchBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelTop, BorderLayout.NORTH);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
