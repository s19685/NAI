package zad1;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {

    private static final int SIZE = 4;
    private static List<String[]> TRAINING_SET;
    private static List<String[]> TEST_SET;

    public static void main(String[] args) {
	// write your code here
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
        training();
        testing();

        Bayes bayes = new Bayes(TRAINING_SET,TEST_SET);

        System.out.println(Bayes.findAllfor(0.2,3));
        System.out.println(bayes);

        Scanner scanner = new Scanner(System.in);

        while (true){

            double [] vector = new double[SIZE];
            System.out.println("Podaj wektor skladajacy sie z " + SIZE + "argumentow:");
            for (int i = 0; i < SIZE ; i++) {
                System.out.println("podaj " + (i + 1) + " argument");
                vector[i] = scanner.nextDouble(); }
            bayesClassifier(vector);
        }
    }

    private static void testing() {
    }

    private static void training() {

    }

    private static void bayesClassifier(double[] vector) {

    }
}
