package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {


    public static List<Example> POINTS;
    public static List<double[]> CENTROIDS;
    public static List<Iris> TREINING_SET;

    public static void main(String[] args) {
        // write your code here

        POINTS = new ArrayList<>();
        TREINING_SET = new ArrayList<>();

        try {
            List<String> arguments = Files.readAllLines(Paths.get("iris_training.txt"));
            for (String s : arguments) {
                String[] stab = s.replaceAll(",", ".").replaceAll(" ", "").split("\t");
                double[] point = Arrays.stream(Arrays.copyOf(stab, stab.length - 1)).mapToDouble(Double::valueOf).toArray();
                POINTS.add(new Example(point));
                TREINING_SET.add(new Iris(point, stab[stab.length - 1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            CENTROIDS = new ArrayList<>();

            System.out.println("Podaj wartosc k");
            int k = scanner.nextInt();

            randomCentroids(k);
            clustering();

            System.out.println(POINTS);
            groupCounting();

        }
    }

    private static void groupCounting() {
        int all;
        int[] iris = new int[3];
        double element, enthropy;

        for (int i = 0; i < CENTROIDS.size(); i++) {
            enthropy = 0.0;
            iris[0] = 0;
            iris[1] = 0;
            iris[2] = 0;
            for (Example e : POINTS) {
                int index = POINTS.indexOf(e);
                String name = TREINING_SET.get(index).getName();
                if (e.getCentroindIndex() == i) {
                    if (e.getPoints() == TREINING_SET.get(index).getPoints() && name.equals("Iris-setosa")) iris[0]++;
                    if (e.getPoints() == TREINING_SET.get(index).getPoints() && name.equals("Iris-virginica")) iris[1]++;
                    if (e.getPoints() == TREINING_SET.get(index).getPoints() && name.equals("Iris-versicolor")) iris[2]++;
                }
            }
            all = iris[0] + iris[1] + iris[2];
            for (int j = 0; j < iris.length; j++) {
                double part = (double) iris[j] / all;
                element = part == 0 ? 0 : part * Math.log(part) / Math.log(2);
                enthropy += element;
            }

            System.out.println("GROUP " + (i + 1));
            System.out.println(Arrays.toString(CENTROIDS.get(i)));
            System.out.println("Iris-setosa " + iris[0] + ", versicolor " + iris[1] + ", virginica " + iris[2]);
            System.out.println("Entropia: " + -1 * enthropy);
        }

    }

    private static void clustering() {
        int error;
        int it = 1;
        do {
            error = 0;
            for (Example e : POINTS) {
                int lastIndex = e.getCentroindIndex();
                e.setCentroindIndex(e.nearestCentroid(CENTROIDS));
                if (lastIndex != e.getCentroindIndex()) error++;
            }

            for (int i = 0; i < CENTROIDS.size(); i++) {
                double[] newcentroid = detectCentroid(i);
                if (newcentroid != null) CENTROIDS.set(i, newcentroid);
            }

            System.out.println("Iteracja " + it++);
            for (int i = 0; i < CENTROIDS.size(); i++) {
                double[] center = detectCentroid(i);
                System.out.println("Group" + (i + 1) + " " + centerSquareDistance(i, center));
            }

        } while (error != 0);

    }

    private static double centerSquareDistance(int i, double[] center) {
        double result = 0.0;
        for (Example e : POINTS) {
            if (e.getCentroindIndex() == i) {
                result += Math.pow(e.countDistance(center), 2);
            }
        }
        return result;
    }

    private static double[] detectCentroid(int index) {
        double[] result = new double[4];
        int devider = 0;
        for (Example e : POINTS) {
            if (e.getCentroindIndex() == index) {
                devider++;
                for (int i = 0; i < result.length; i++) {
                    result[i] += e.getPoints()[i];
                }

            }
        }

        int finalDevider = devider;
        return Arrays.stream(result).map(d -> d / finalDevider).toArray();
    }

    private static void randomCentroids(int k) {
        System.out.println("CENTROIDS");

        for (int i = 0; i < k; i++) {
            CENTROIDS.add(new double[]{Math.random() * 3 + 4, Math.random() * 2 + 2, Math.random() * 6 + 1, Math.random() * 3});
            System.out.println(Arrays.toString(CENTROIDS.get(i)));
        }
        System.out.println();
    }
}
