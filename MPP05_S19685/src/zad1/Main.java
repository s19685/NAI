package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final int SIZE = 4;
    private static List<String[]> TRAINING_SET;
    private static List<String[]> TEST_SET;

    public static void main(String[] args) {

        TRAINING_SET = new ArrayList<>();
        TEST_SET = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("iris_training.txt"));
            List<String> test = Files.readAllLines(Paths.get("iris_test.txt"));
            for (String line : lines) TRAINING_SET.add(line.replace(" ", "").split("\t"));
            for (String line : test) TEST_SET.add(line.replace(" ", "").split("\t"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bayes bayes = new Bayes(TRAINING_SET);
        bayes.testing(TEST_SET);

        Scanner scanner = new Scanner(System.in);
        while (true){

            double [] vector = new double[SIZE];
            System.out.println("\nPodaj wektor skladajacy sie z " + SIZE + "argumentow:");
            for (int i = 0; i < SIZE ; i++) {
                System.out.println("podaj " + (i + 1) + " argument");
                vector[i] = scanner.nextDouble();
            }
            System.out.println("\nPodany wektor zaklasyfikowano jako: "+bayes.classify(vector));
        }
    }
}
