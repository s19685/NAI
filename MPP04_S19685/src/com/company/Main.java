package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    public static final int SIZE = 4;
    public static List<Example> POINTS;
    public static List<double[]> CENTROIDS;
    public static List<Iris> TREINING_SET;
    public static List<List<Double>> MEASURES;

    public static void main(String[] args) {

        POINTS = new ArrayList<>();
        TREINING_SET = new ArrayList<>();
        MEASURES = new ArrayList<>();

        try {
            List<String> arguments = Files.readAllLines(Paths.get("iris_training.txt"));
            for (String s : arguments) {
                String[] stab = s.replaceAll(",", ".")
                        .replaceAll(" ", "")
                        .split("\t");
                double[] point = Arrays.stream(Arrays.copyOf(stab, SIZE))
                        .mapToDouble(Double::valueOf)
                        .toArray();
                for (int i = 0; i < SIZE; i++) {
                    MEASURES.add(new ArrayList<>());
                    MEASURES.get(i).add(point[i]);
                }
                POINTS.add(new Example(point));
                TREINING_SET.add(new Iris(point, stab[SIZE]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            CENTROIDS = new ArrayList<>();

            System.out.println("\nPodaj wartosc k");
            int k = scanner.nextInt();

            randomCentroids(k);
            clustering();
            System.out.println("========================================");
            System.out.println(POINTS);
            System.out.println("========================================");
            groupCounting();

        }
    }

    private static void groupCounting() {
        int all;
        int[] iris;
        double enthropy;

        for (int i = 0; i < CENTROIDS.size(); i++) {
            iris = new int[3];
            for (Example e : POINTS) {
                int index = POINTS.indexOf(e);
                String name = TREINING_SET.get(index).getName();
                double[] points = TREINING_SET.get(index).getPoints();

                if (e.getCentroindIndex() == i) {
                    if (e.getPoints() == points && name.equals("Iris-setosa")) iris[0]++;
                    if (e.getPoints() == points && name.equals("Iris-virginica")) iris[1]++;
                    if (e.getPoints() == points && name.equals("Iris-versicolor")) iris[2]++;
                }
            }

            all = iris[0] + iris[1] + iris[2];

            Integer maks = Collections.max(Arrays.asList(Arrays.stream(iris).boxed().toArray(Integer[]::new)));
            int rest = all - maks;
            double p1 = (double) maks / all;
            double p2 = (double) rest / all;

            enthropy = rest == 0 ? 0 : -1 * (p1 * Math.log(p1) / Math.log(2) + p2 * Math.log(p2) / Math.log(2));

            System.out.println("GROUP " + (i + 1));
            System.out.println(Arrays.toString(CENTROIDS.get(i)));
            System.out.println("Iris-setosa " + iris[0] + ", versicolor " + iris[1] + ", virginica " + iris[2]);
            System.out.println("Entrophy: " + enthropy);
            System.out.println();
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

            System.out.println("Iteration: " + it++);
            for (int i = 0; i < CENTROIDS.size(); i++) {
                double[] center = detectCentroid(i);
                System.out.println("Group " + (i + 1) + ": " + centerSquareDistance(i, center));
            }
            System.out.println("Error counter: " + error);
            System.out.println();

        } while (error != 0);

    }

    private static double centerSquareDistance(int i, double[] center) {
        double result = 0.0;
        for (Example e : POINTS) { if (e.getCentroindIndex() == i) { result += Math.pow(e.countDistance(center), 2); } }
        return result;
    }

    private static double[] detectCentroid(int index) {
        double[] result = new double[SIZE];
        int devider = 0;
        for (Example e : POINTS) {
            if (e.getCentroindIndex() == index) {
                for (int i = 0; i < SIZE; i++) { result[i] += e.getPoints()[i]; }
                devider++;
            }
        }

        int finalDevider = devider;
        return Arrays.stream(result).map(d -> d / finalDevider).toArray();
    }

    private static void randomCentroids(int k) {
        System.out.println("CENTROIDS");

        for (int i = 0; i < k; i++) {
            CENTROIDS.add(generateDoubleArray());
            System.out.println(Arrays.toString(CENTROIDS.get(i)));
        }
        System.out.println();
    }

    private static double[] generateDoubleArray() {
        double ds[] = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            double max = Collections.max(MEASURES.get(i));
            double min = Collections.min(MEASURES.get(i));
            ds[i] = min + (max - min) * Math.random();
        }

        return ds;
    }
}
