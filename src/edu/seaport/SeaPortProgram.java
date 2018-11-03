package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class SeaPortProgram extends JFrame {

    private World world;
    private JFrame frame;
    private JButton fileReadBtn, searchBtn;
    private JTextArea textOutput;
    private TextField searchBox;
    private String[] searchOptions;
    private JComboBox<String> searchDropdown;

    private SeaPortProgram() {

        this.createGui();

        this.fileReadBtn.addActionListener(e -> parseFile());
        this.searchBtn.addActionListener(e -> searchParsedWorld(searchBox.getText().trim(), searchDropdown.getSelectedIndex()));

    }

    private void searchParsedWorld(String searchText, int dropdownIndex) {

        String searchResults = null;

        // Check if world is populated.
        if(this.world != null) {

            // Check if the search box is empty or if the dropdown is unselected.
            if (!searchText.equals("") && dropdownIndex != 0) {

                switch(dropdownIndex) {
                    case 1: // By name
                        searchResults = this.worldSearchResults(searchText, dropdownIndex);
                    case 2: // By index
                        searchResults = this.worldSearchResults(searchText, dropdownIndex);
                        break;
                    case 3: // By skill
                        searchResults = this.worldSearchResults(searchText, dropdownIndex);
                        break;
                    default:
                        break;
                }

                System.out.println(searchResults);

            } else {
                JOptionPane.showMessageDialog(null, "Search text or dropdown selection missing!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please choose a file!");
        }

    }

    private String worldSearchResults(String searchText, int dropdownIndex) {

        StringBuilder results = new StringBuilder();
        Method getParam = null;

        if(dropdownIndex != 3) {
            try {
                switch(dropdownIndex) {
                    case 1:
                        getParam = Thing.class.getDeclaredMethod("getName");
                        break;
                    case 2:
                        getParam = Thing.class.getDeclaredMethod("getIndex");
                        break;
                }

                for (Thing item : this.world.getAllThings()) {

                    String parameter = getParam.invoke(item).toString();

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
                System.out.println("Error: " + ex);
            }
        } else {

            for (SeaPort port : this.world.getPorts()) {

                for (Person person : port.getPersons()) {

                    if (person.getSkill().equals(searchText)) {

                        results.append(person.getName());
                        results.append(" (id #");
                        results.append(person.getIndex());
                        results.append(")\n");
                    }
                }
            }
        }

        return results.toString();
    }

    private void parseFile() {
        try {
            FileReader fileReader = new FileReader(this.getLocalFile());
            Scanner scanner = new Scanner(fileReader);
            this.world = new World(scanner);
            this.textOutput.setText(String.format("%s", this.world.toString()));
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private File getLocalFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.showOpenDialog(null);
        fileChooser.setVisible(true);
        return fileChooser.getSelectedFile();
    }


    private void createGui() {

        this.frame = new JFrame("SeaPort Program");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(600, 600));

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new GridLayout(1, 5, 5, 5));

        this.fileReadBtn = new JButton("Select File");
        this.searchBtn = new JButton("Search");


        JLabel searchBoxLabel = new JLabel("Search:", JLabel.RIGHT);
        this.searchBox = new TextField();
        this.searchBox = new TextField("", 10);

        this.searchOptions = new String[] {"", "name", "index", "skill"};
        this.searchDropdown = new JComboBox<>(this.searchOptions);

        // Main text output area styling
        this.textOutput = new JTextArea();
        this.textOutput.setEditable(false);
        this.textOutput.setFont (new java.awt.Font ("Monospaced", Font.PLAIN, 12));
        this.textOutput.setLineWrap(true);

        //TODO: Add split plane when searching

        JScrollPane scrollPane = new JScrollPane(textOutput);

        // Panel for the top menu bar
        panelTop.add(fileReadBtn);
        panelTop.add(searchBoxLabel);
        panelTop.add(searchBox);
        panelTop.add(searchDropdown);
        panelTop.add(searchBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelTop, BorderLayout.NORTH);

        this.frame.add(panel);
        this.frame.pack();
        this.frame.setVisible(true);
    }


    public static void main(String[] args) {
        new SeaPortProgram();
    }
}
