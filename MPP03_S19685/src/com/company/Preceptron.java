package com.company;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class Preceptron {
    private String name;
    private Map<String,Integer> symbolsCount;


    public Preceptron(String name){
        this.name = name;
        learn(new File("data/"+name));
    }

    private void learn(File directory) {
        for (File file : directory.listFiles() ) studyFile(file);
    }


    private void studyFile(File file) {


        try {
            Files.readAllLines(Paths.get("data/"+name+"/"+file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner scanner = new Scanner(file);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public boolean check(String test) {
        return true;
    }


    @Override
    public String toString() {
        return "Preceptron{"+ name + "}";
    }

    public String getName() {
        return name;
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
