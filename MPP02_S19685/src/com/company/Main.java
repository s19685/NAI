package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        List<Training> trainingList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("iris_training.txt"));
            //List<String> test = Files.readAllLines(Paths.get("iris_test.txt"));
            for (String line : lines) {
                String[] lineTab = line.replace(" ", "").split("\t");
                List<Double> argss = new ArrayList<>();
                for (int i = 0; i < lineTab.length - 1; i++) argss.add(Double.valueOf(lineTab[i]));
                int output = lineTab[lineTab.length - 1].equals("Iris-setosa") ? 1 : 0;

//                trainingList.add(new Training(argss,output));
                trainingList.add(new Training(Arrays.asList(Arrays.stream(Arrays.copyOf(lineTab, lineTab.length - 1)).map(s->Double.valueOf(s))), lineTab[lineTab.length - 1].equals("Iris-setosa") ? 1 : 0));
            }

            //for (String line : lines) TRAINING_SET.add(line.replace(" ", "").split("\t"));
            // for (String line : test) TEST_SET.add(line.replace(" ", "").split("\t"));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
