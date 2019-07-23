package com.company;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduisez le nombre de formis :");
        int nb_ant = sc.nextInt();
        System.out.println("Introduisez le nombre d'iterations :");
        int nb_iteration = sc.nextInt();
        int Q = 1;
        float P = (float) 0.9;
        float alpha = (float) 0.3;
        float beta = (float) 0.7;
        AntSystem.setP(P);
        AntSystem.setQ(Q);
        AntSystem.setBeta(beta);
        AntSystem.setAlpha(alpha);


        // Si le ficher de test suit une représentation de matrice de distances alors on utilise cette partie du code.
        //DEBUT

        String Test_file_name = "dantzig42.tsp";

        System.out.println(Test_file_name);
        float[][] cities_distance = Load.loadTSPDistance(Test_file_name);

        float[][] pheromone_intensities = new float[cities_distance.length][cities_distance.length];
        float[][] pheromone_intensities_perT = new float[cities_distance.length][cities_distance.length];

        AntSystem.setPheromone_intensities(pheromone_intensities);
        AntSystem.setPheromone_intensities_perT(pheromone_intensities_perT);
        AntSystem.setCities_distance(cities_distance);
        double startTime = System.currentTimeMillis();
        Ant chosen_ant = AntSystem.antOptimisation(nb_iteration, nb_ant);
        double time = System.currentTimeMillis() - startTime;
        String parameters = nb_ant + " " + nb_iteration + " " + AntSystem.getAlpha() + " " + AntSystem.getBeta() + " " +
                AntSystem.getQ() + " " + AntSystem.getP();
        Resultat result = new Resultat(chosen_ant.visited_towns, chosen_ant.length_of_path, time, parameters);
        System.out.println(result + "\n" + Test_file_name);

        //FIN


        // Si le ficher de test suit une représentation de coordonnées alors on utilise cette partie du code.
        //DEBUT

        /*String Test_file_name = "berlin52.tsp";

        System.out.println(Test_file_name);
        ArrayList<Point2D> cities = Load.loadTSPLib(Test_file_name);
        float[][] pheromone_intensities = new float[cities.size()][cities.size()];
        float[][] pheromone_intensities_perT = new float[cities.size()][cities.size()];
        AntSystem.setPheromone_intensities(pheromone_intensities);
        AntSystem.setPheromone_intensities_perT(pheromone_intensities_perT);
        AntSystem.setCities(cities);
        double startTime = System.currentTimeMillis();
        Ant chosen_ant = AntSystem.antOptimisationPoint2D(nb_iteration, nb_ant);
        double time = System.currentTimeMillis() - startTime;
        String parameters = nb_ant + " " + nb_iteration + " " + AntSystem.getAlpha() + " " + AntSystem.getBeta() + " " +
                AntSystem.getQ() + " " + AntSystem.getP();
        Resultat result = new Resultat(chosen_ant.visited_towns, chosen_ant.length_of_path, time, parameters);
        System.out.println(result + "\n" + Test_file_name);*/
        //FIN
    }
}
