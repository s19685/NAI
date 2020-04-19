package com.company;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class Preceptron {
    private String name;
    private int[] lettersCount;
    private static final char STARTING_CHAR = 'a';


    public Preceptron(String name) {
        this.name = name;
        System.out.println(this.toString());
        lettersCount = new int[26];
        learn(new File("data/" + name));
    }

    private void learn(File directory) {
        for (File file : directory.listFiles()) studyFile(file);
        System.out.println();
        System.out.println(showLetters());
    }


    private void studyFile(File file) {

        String line = "";
        try {
            line = String.valueOf(Files.readAllLines(Paths.get(file.toString())))
                         .replaceAll("[^a-zA-Z]", "").toLowerCase();
            System.out.println(line);
        } catch (MalformedInputException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        char letter = STARTING_CHAR;
        for (int i = 0; i < lettersCount.length; i++) {
            lettersCount[i] = countLetter(line, letter);
            letter++;
        }


    }

    private int countLetter(String line, char letter) {
        int result = 0;
        for (int i = 0; i < line.length(); i++)
            if (line.charAt(i) == letter) result++;
        return result;
    }

    public boolean check(String test) {
        return true;
    }


    @Override
    public String toString() {
        return "Preceptron{" + name + "}";
    }

    public String getName() {
        return name;
    }

    public String showLetters(){
        String result = "";
        char c = STARTING_CHAR;
        for (int i =0; i<lettersCount.length ; i++){
            result+= c + ": "+lettersCount[i]+" ";
            c++;
        }

        return result;
    }


//    private static void trainingAction(List<Training> trainingList) {
//
//        int iteration = 0;
//        int error;
//        int d;
//        do {
//            iteration++;
//            error = 0;
//            for (Training t : trainingList) {
//                boolean test = check(t.getArguments());
//                if (test == t.getOutput()) continue;
//                d = !test && t.getOutput() ? 1 : -1;
//                for (int i = 0; i < ARGSIZE; i++) {
//                    weights[i] += d * LEARNING_RATE * t.getArguments()[i];
//                }
//                weights[weights.length - 1] += d * LEARNING_RATE;
//                error += d * d;
//            }
//            System.out.println("it" + iteration + " " + Math.sqrt((double) error / trainingList.size()));
//        } while (error != 0);
//
//        System.out.println(weights[0]+"w + "+weights[1]+"x + "+weights[2]+"y + "+weights[3]+"z + "+weights[4]+" = 0" );
//    }
//
//    private static boolean check(Double[] vector) {
//        double sum = 0;
//        for (int i = 0; i < vector.length; i++) sum += vector[i] * weights[i];
//        sum += weights[weights.length - 1];
//
//        return sum >= 0;
//    }
}
