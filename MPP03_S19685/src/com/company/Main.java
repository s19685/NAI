package com.company;

import java.io.File;
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
            preceptrons.add(new Preceptron(lang, directories));
            options += lang + " ";
        }


        Scanner scanner = new Scanner(System.in);
        while (true) {
            String test = "";
            System.out.println("Wpisz tekst w jednym z jezykow: " + options);


            test = scanner.nextLine();

            for (Preceptron p : preceptrons ) System.out.print(!p.check(test) ? "" : "Podany tekst jest w jezyku: "+p.getLang());
            System.out.println();
            System.out.println();

        }
    }
}
