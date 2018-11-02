package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class SeaPortProgram extends JFrame {

    private World world;
    private JFrame frame;
    private JButton fileReadBtn, searchBtn;
    private JTextArea textOutput;

    private SeaPortProgram() {

        this.createGui();

        fileReadBtn.addActionListener(e -> parseFile());

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

        frame = new JFrame("SeaPort Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        fileReadBtn = new JButton("Select File");
        searchBtn = new JButton("Search");

        //TODO: Add search input field


        //TODO: add dropdown search parameters

        textOutput = new JTextArea();
        textOutput.setEditable(false);
        textOutput.setFont (new java.awt.Font ("Monospaced", Font.PLAIN, 12));
        textOutput.setLineWrap(true);

        //TODO: Add split plane when searching

        JScrollPane scrollPane = new JScrollPane(textOutput);

        panel.add(fileReadBtn);
        panel.add(searchBtn);
        panel.add(scrollPane);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new SeaPortProgram();
    }
}
