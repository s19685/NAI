package com.company;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static int ARGSIZE;
    public static int COUNTER;


    public static void main(String[] args) {

        List<Training> trainingList = new ArrayList<>();
        List<String[]> testingList = new ArrayList<>();


        try {
            List<String> lines = Files.readAllLines(Paths.get("iris_training.txt"));
            List<String> test = Files.readAllLines(Paths.get("iris_test.txt"));
            for (String line : lines) {
                String[] lineTab = line.replace(" ", "").split("\t");
                ARGSIZE = lineTab.length - 1;
                trainingList.add(new Training(toDoubleArray(lineTab), lineTab[ARGSIZE].equals("Iris-setosa")));
            }
            for (String line : test) testingList.add(line.replace(" ", "").split("\t"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Training t : trainingList) t.show();

        trainingAction(trainingList);

        testingTestSet();
        System.out.println("xd");

        COUNTER = 0;
        for (String[] t : testingList) {
            boolean result = check(toDoubleArray(Arrays.copyOf(t, ARGSIZE)));
            if (t[t.length - 1].equals("Iris-setosa") && result) COUNTER++;
            if (!t[t.length - 1].equals("Iris-setosa") && !result) COUNTER++;
        }
        System.out.println("prawidłowo zaklasyfikowanych przykładów: " + COUNTER + ", dokładność eksperymentu: " + (double)COUNTER/trainingList.size() + "%");

        Scanner scanner = new Scanner(System.in);
        double[] vector = new double[ARGSIZE];
        while (true) {

            for (int i = 0; i < ARGSIZE; i++) {
                System.out.println("Podaj " + (i + 1) + " argument");
                vector[i] = scanner.nextDouble();
            }

            for (double d : vector) System.out.print(d + " ");
        }
    }

    private static void testingTestSet() {
    }

    private static void trainingAction(List<Training> trainingList) {
    }

    private static boolean check(Double[] vector) {
        return true;
    }


    public static Double[] toDoubleArray(String[] tab) {
        return Stream.of(Arrays.copyOf(tab, ARGSIZE))
                .map(s -> Double.valueOf(s.replace(",", ".")))
                .toArray(Double[]::new);
    }
}
