package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<String[]> TRAINNG_SET;

    public static void main(String[] args) {
	// write your code here

        TRAINNG_SET = new ArrayList<>();


        try {
            List<String> lines =  Files.readAllLines(Paths.get("iris_training.txt"));
            for (String line : lines) TRAINNG_SET.add(line.split("\t"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        show();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w kNN, podaj k:");
        int k = scanner.nextInt();
        int vectorSize = TRAINNG_SET.get(0).length-1;

        System.out.println("Podaj wektor "+ vectorSize + " lub nazwe pliku testowego");
        String vector = scanner.nextLine();
        scanner.nextInt();
        System.out.println("wtf");
    }


    public static void show(){
        for (String[] s : TRAINNG_SET ) {
            System.out.print("[ ");
            for (String string : s ) System.out.print(string+" ");
            System.out.println("]");

        }
    }
}
