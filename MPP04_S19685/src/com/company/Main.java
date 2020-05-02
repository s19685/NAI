package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {


    public static List<Example> POINTS;

    public static void main(String[] args) {
	// write your code here

        POINTS = new ArrayList<>();

        try {
            List<String> arguments = Files.readAllLines(Paths.get("iris_training.txt"));
            for (String s : arguments){
                String[] stab = s.replaceAll(",",".").replaceAll(" ","").split("\t");
                double[] point = Arrays.stream(Arrays.copyOf(stab,stab.length-1)).mapToDouble(Double::valueOf).toArray();
                POINTS.add(new Example(point));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }






        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Podaj wartosc k");
            int k = scanner.nextInt();

            double[] centroids = new double[k];

            randomLocations(centroids);
            clustering(centroids);


        }
    }

    private static void clustering(double[] centroids) {

    }

    private static void randomLocations(double[] centroids) {


    }
}
