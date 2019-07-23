package com.company;

import java.util.ArrayList;

public class Ant {
    int init_town;
    double length_of_path = 0;
    ArrayList<Integer> visited_towns = new ArrayList<>();

    public Ant(int town){
        this.init_town = town;
        visited_towns.add(town);
    }
}
