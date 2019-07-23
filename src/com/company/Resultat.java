package com.company;

import java.util.ArrayList;

public class Resultat {
    ArrayList<Integer>  chemin;
    double cout;
    double temps;
    String parametres;
    public Resultat(ArrayList<Integer> chemin, double cout, double temps, String parametres){
        this.chemin=chemin;
        this.cout=cout;
        this.temps=temps;
        this.parametres=parametres;

    }

    @Override
    public String toString() {
        return "Resultat{" +
                "cout=" + cout +
                ", temps=" + temps +
                ", parametres='" + parametres + '\'' +
                '}';
    }
}
