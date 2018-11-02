package edu.seaport;

import java.util.ArrayList;
import java.util.Scanner;

public class Job extends Thing {

    private double duration;
    private ArrayList<String> requirements;

    Job(Scanner sc) {
        super(sc);
    }

}
