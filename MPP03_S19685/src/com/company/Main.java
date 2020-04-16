package com.company;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here

        List<Preceptron> preceptrons = new ArrayList();
        File file = new File("data");
        String[] directories = file.list((x, d) -> new File(x, d).isDirectory());
        String options = "";

        for (String lang : directories) {
            preceptrons.add(new Preceptron(lang));
            System.out.println(lang);
            options += lang + " ";
        }


        while (true) {
            String test = "";
            System.out.println("Wpisz tekst w jednym z jezykow: " + options);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                test += scanner.nextLine();
                System.out.println(test);
                //if(scanner.nextLine().equals("xd")) System.out.println("xd");

                //ZROB test dla wczytanego testu
                //PODAJ WYNIK TESTU
            }

            System.out.println(test);
        }
    }
}
