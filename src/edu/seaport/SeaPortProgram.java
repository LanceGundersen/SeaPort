package edu.seaport;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SeaPortProgram extends JFrame {

    private World world;

    private SeaPortProgram() {

        File file = this.getLocalFile();
        if(file != null) {
            this.createGui();
        } else {
            System.exit(0);
        }
    }

    private File getLocalFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.showOpenDialog(null);
        fileChooser.setVisible(true);
        return fileChooser.getSelectedFile();
    }


    private void createGui() {

        JFrame frame = new JFrame("SeaPort Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        JTextArea exampleText = new JTextArea("Hello World!");
        exampleText.setFont (new java.awt.Font ("Monospaced", Font.PLAIN, 12));

        panel.add(exampleText);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        System.out.println("Hello World!");
        new SeaPortProgram();
    }
}
