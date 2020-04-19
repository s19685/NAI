package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preceptron {
    private static final double LEARNING_RATE = 0.1;
    private double[] weights;
    private String lang;
    private String[] others;
    private int[] lettersCount;
    private static final char STARTING_CHAR = 'a';
    private List<Training> TRAINING_LIST;


    public Preceptron(String lang, String[] dirs) {
        this.lang = lang;
        others = Arrays.stream( dirs).filter(l -> !l.equals(lang)).toArray(String[]::new);
        System.out.println();
        System.out.println(this.toString());
        TRAINING_LIST = new ArrayList<>();
        learn(new File("data/" + lang),true);
        for (String otherLang : others) learn(new File("data/"+otherLang), false);
        weights = new double[27];
        for (int i = 0; i < weights.length; i++) weights[i] = Math.random();

        for (Training t : TRAINING_LIST ) t.show();
        trainingAction(TRAINING_LIST);
    }

    private void learn(File directory, boolean correctLang) {
        for (File file : directory.listFiles()) studyFile(file,correctLang);
    }


    private void studyFile(File file, boolean verdict) {
        lettersCount = new int[26];


        String line = "";
        try {
            line = String.valueOf(Files.readAllLines(Paths.get(file.toString())))
                         .replaceAll("[^a-zA-Z]", "").toLowerCase();
//            System.out.println(line);
        } catch (MalformedInputException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        lettersCount = getLettersCounts(line);
        if(lettersCount[0]==0) return;
        TRAINING_LIST.add(new Training(lettersCount,verdict));

    }

    private int countLetter(String line, char letter) {
        int result = 0;
        for (int i = 0; i < line.length(); i++)
            if (line.charAt(i) == letter) result++;
        return result;
    }

    private int[] getLettersCounts(String line){
        int[] result = new int[26];
        char letter = STARTING_CHAR;
        for (int i = 0; i < result.length; i++) {
            result[i] = countLetter(line, letter);
            letter++;
        }

        return result;
    }

    private void trainingAction(List<Training> trainingList) {

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
                for (int i = 0; i < t.getArguments().length; i++) {
                    weights[i] += d * LEARNING_RATE * t.getArguments()[i];
                }
                weights[weights.length - 1] += d * LEARNING_RATE;
                error += d * d;
            }
            System.out.println("it" + iteration + " " + Math.sqrt((double) error / trainingList.size()));
        } while (error != 0);

        char c = 'a';

        for (double di: weights
             ) {
            System.out.print(di+""+c+" ");
            c++;
        }
        System.out.println(" = 0" );
    }

    public boolean check(String test) {
        test = test.replaceAll("[^a-zA-Z]", "").toLowerCase();
        int[] vector;
        vector = getLettersCounts(test);

        return check(vector);
    }

    private boolean check(int[] vector) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) sum += vector[i] * weights[i];
        sum += weights[weights.length - 1];

        return sum >= 0;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return "Preceptron{" + lang + "}";
    }

}
