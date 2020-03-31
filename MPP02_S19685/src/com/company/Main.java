package com.company;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static int ARGSIZE;
    public static int COUNTER;
    public static List<String[]> TESTING_LIST;
    public static final double LEARNING_RATE = 0.1;
    public static double[] weights;


    public static void main(String[] args) {

        List<Training> trainingList = new ArrayList<>();
        TESTING_LIST = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("iris_training.txt"));
            List<String> test = Files.readAllLines(Paths.get("iris_test.txt"));
            for (String line : lines) {
                String[] lineTab = line.replace(" ", "").split("\t");
                ARGSIZE = lineTab.length - 1;
                trainingList.add(new Training(toDoubleArray(lineTab), lineTab[ARGSIZE].equals("Iris-setosa")));
            }
            for (String line : test) TESTING_LIST.add(line.replace(" ", "").split("\t"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Training t : trainingList) t.show();

        weights = new double[ARGSIZE + 1];
        for (int i = 0; i < weights.length; i++) weights[i] = Math.random();

        trainingAction(trainingList);

        testingTestSet();

        Scanner scanner = new Scanner(System.in);
        Double[] vector = new Double[ARGSIZE];
        while (true) {

            for (int i = 0; i < ARGSIZE; i++) {
                System.out.println("Podaj " + (i + 1) + " argument");
                vector[i] = scanner.nextDouble();
            }

            for (double d : vector) System.out.print(d + " ");
            System.out.println(check(vector) ? ": Iris-setosa" : ": \u001B[31m Nie Iris-setosa \u001B[0m");
            System.out.println();
        }
    }

    private static void testingTestSet() {

        COUNTER = 0;
        for (String[] t : TESTING_LIST) {
            boolean result = check(toDoubleArray(Arrays.copyOf(t, ARGSIZE)));
            if (t[t.length - 1].equals("Iris-setosa") && result) COUNTER++;
            if (!t[t.length - 1].equals("Iris-setosa") && !result) COUNTER++;
        }
        System.out.println("prawidłowo zaklasyfikowanych przykładów: " + COUNTER +
                           ", dokładność eksperymentu: " + (int) ((double) COUNTER / TESTING_LIST.size() * 100) + "%");

    }

    private static void trainingAction(List<Training> trainingList) {

        int iteration = 0;
        int error;
        int d;
        do {
            iteration++;
            error = 0;
            for (Training t : trainingList) {
                boolean test = check(t.getArguments());
                if (test == t.getOutput()) continue;
                d = !test && t.getOutput() ? 1 : -1;
                for (int i = 0; i < ARGSIZE; i++) {
                    weights[i] += d * LEARNING_RATE * t.getArguments()[i];
                }
                weights[weights.length - 1] += d * LEARNING_RATE;
                error += d * d;
            }
            System.out.println("it" + iteration + " " + Math.sqrt((double) error / trainingList.size()));
        } while (error != 0);

        System.out.println(weights[0]+"w + "+weights[1]+"x + "+weights[2]+"y + "+weights[3]+"z + "+weights[4]+" = 0" );
    }

    private static boolean check(Double[] vector) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) sum += vector[i] * weights[i];
        sum += weights[weights.length - 1];

        return sum >= 0;
    }

    public static Double[] toDoubleArray(String[] tab) {
        return Stream.of(Arrays.copyOf(tab, ARGSIZE))
                .map(s -> Double.valueOf(s.replace(",", ".")))
                .toArray(Double[]::new);
    }
}
