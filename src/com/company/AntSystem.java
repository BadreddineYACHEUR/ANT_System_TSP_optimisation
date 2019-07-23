package com.company;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AntSystem {
    public static int getQ() {
        return Q;
    }

    public static float getP() {
        return P;
    }

    public static void setQ(int q) {
        Q = q;
    }

    public static void setP(float p) {
        P = p;
    }

    public static float getAlpha() {
        return alpha;
    }

    public static void setAlpha(float alpha) {
        AntSystem.alpha = alpha;
    }

    public static float getBeta() {
        return beta;
    }

    public static void setBeta(float beta) {
        AntSystem.beta = beta;
    }

    public static float[][] getCities_distance() {
        return cities_distance;
    }

    public static void setCities_distance(float[][] cities_distance) {
        AntSystem.cities_distance = cities_distance;
    }

    public static float[][] getPheromone_intensities() {
        return pheromone_intensities;
    }

    public static void setPheromone_intensities(float[][] pheromone_intensities) {
        AntSystem.pheromone_intensities = pheromone_intensities;
    }

    public static float[][] getPheromone_intensities_perT() {
        return pheromone_intensities_perT;
    }

    public static void setPheromone_intensities_perT(float[][] pheromone_intensities_perT) {
        AntSystem.pheromone_intensities_perT = pheromone_intensities_perT;
    }

    public static ArrayList<Ant> getAnts() {
        return ants;
    }

    public static void setAnts(ArrayList<Ant> ants) {
        AntSystem.ants = ants;
    }

    public static ArrayList<Point2D> getCities() {
        return cities;
    }

    public static void setCities(ArrayList<Point2D> cities) {
        AntSystem.cities = cities;
    }

    private static int Q;
    private static float P;
    private static float alpha;
    private static float beta;
    private static ArrayList<Point2D> cities;
    private static float[][] cities_distance;
    private static float[][] pheromone_intensities;
    private static float[][] pheromone_intensities_perT;
    private static ArrayList<Ant> ants = new ArrayList<>();
    // Point2D Algorithms

    static Ant antOptimisationPoint2D(int nb_iterations, int nb_ants) {
        int dimension = cities.size();
        init_pheromone(dimension);

        for (int t = 0; t < nb_iterations; t++) {
            ants = CreateAnts(nb_ants, dimension);
            for (Ant ant : ants) {
                while (ant.visited_towns.size() < dimension) {
                    double prob_visit = 0;
                    int elected = dimension + 5;

                    for (int j = 0; j < dimension; j++) {
                        if (!ant.visited_towns.contains(j)) {
                            double prob_j;
                            prob_j = Math.pow(pheromone_intensities[ant.init_town][j], alpha) *
                                    Math.pow(1 / cities.get(ant.init_town).distance(cities.get(j)), beta);
                            prob_j = prob_j / sommeMatrix(pheromone_intensities, cities, ant.init_town);
                            if (prob_j >= prob_visit) {
                                prob_visit = prob_j;
                                elected = j;
                            }
                        }
                    }
                    ant.visited_towns.add(elected);
                    ant.length_of_path += cities.get(elected).distance(cities.get(ant.init_town));
                    ant.init_town = elected;
                }
                update_pheromone(ant.visited_towns, ant.length_of_path);


            }
            pheromone_vaporisation(dimension);
        }
        Ant chosen = ants.get(0);
        for (Ant ant : ants) {
            if(ant.length_of_path < chosen.length_of_path)
                chosen = ant;
        }
        return chosen;

    }
    private static double sommeMatrix(float[][] pheromone_intensities, ArrayList<Point2D> cities, int head_town) {
        double result = 0;
        for (int i = 0; i < cities.size(); i++) {
            result += Math.pow(pheromone_intensities[head_town][i], alpha) *
                    Math.pow(cities.get(head_town).distance(cities.get(i)), beta);
        }
        return result;
    }



    // Distance Matrix Algorithms
    private static ArrayList<Ant> CreateAnts(int nb_ants, int dimension) {
        int town;
        ArrayList<Integer> chosen_towns = new ArrayList<>();
        town = (int) (Math.random() * ((dimension - 1) + 1));
        Ant ant = new Ant(town);
        ants.add(ant);
        chosen_towns.add(town);

        for (int i = 1; i < nb_ants; i++) {
            town = (int) (Math.random() * ((dimension - 1) + 1));
            while (chosen_towns.contains(town)) {
                town = (int) (Math.random() * ((dimension - 1) + 1));
            }
            ant = new Ant(town);
            ants.add(ant);
            chosen_towns.add(town);
        }


        return ants;
    }

    static Ant antOptimisation(int nb_iterations, int nb_ants) {
        int dimension = cities_distance.length;
        init_pheromone(dimension);

        for (int t = 0; t < nb_iterations; t++) {
            ants = CreateAnts(nb_ants, dimension);
            for (Ant ant : ants) {
                while (ant.visited_towns.size() < dimension) {
                    double prob_visit = 0;
                    int elected = dimension + 5;

                    for (int j = 0; j < dimension; j++) {
                        if (!ant.visited_towns.contains(j)) {
                            double prob_j;
                            prob_j = Math.pow(pheromone_intensities[ant.init_town][j], alpha) *
                                    Math.pow(1 / cities_distance[Math.min(ant.init_town, j)][Math.max(j, ant.init_town)], beta);
                            prob_j = prob_j / sommeMatrix(pheromone_intensities, cities_distance, ant.init_town);
                            if (prob_j >= prob_visit) {
                                prob_visit = prob_j;
                                elected = j;
                            }
                        }
                    }
                    ant.visited_towns.add(elected);
                    ant.length_of_path += cities_distance[Math.min(ant.init_town, elected)][Math.max(ant.init_town, elected)];
                    ant.init_town = elected;
                }
                update_pheromone(ant.visited_towns, ant.length_of_path);


            }
            pheromone_vaporisation(dimension);
        }
        Ant chosen = ants.get(0);
        for (Ant ant : ants) {
            if(ant.length_of_path < chosen.length_of_path)
                chosen = ant;
        }
        return chosen;

    }

    private static void pheromone_vaporisation(int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                pheromone_intensities[i][j] = P * pheromone_intensities[i][j] + pheromone_intensities_perT[i][j];
            }
        }
    }

    private static void update_pheromone(ArrayList<Integer> visited_towns, double length_of_path) {

        for (int i = 0; i < visited_towns.size(); i++) {
            if (i == visited_towns.size() - 1)
                pheromone_intensities_perT[i][0] += Q / length_of_path;
            else
                pheromone_intensities_perT[i][i + 1] += Q / length_of_path;

        }
    }

    private static void init_pheromone(int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                pheromone_intensities[i][j] = 1;
            }
        }
    }

    private static double sommeMatrix(float[][] pheromone_intensities, float[][] cities_distance, int head_town) {
        double result = 0;
        for (int i = 0; i < cities_distance.length; i++) {
            result += Math.pow(pheromone_intensities[head_town][i], alpha) *
                    Math.pow(cities_distance[Math.min(head_town, i)][Math.max(i, head_town)], beta);
        }
        return result;
    }


}