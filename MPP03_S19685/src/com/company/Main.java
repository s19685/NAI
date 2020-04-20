package com.company;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {

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

            Double[] res = new Double[preceptrons.size()];

            for (int i = 0; i < res.length; i++) {
                res[i] = preceptrons.get(i).check(test);
                System.out.println(preceptrons.get(i).getLang() + " "+res[i]);
            }
            int maxIndex = Arrays.asList(res).indexOf(Collections.max(Arrays.asList(res)));

            System.out.println("Podany tekst jest w jezyku: "+ preceptrons.get(maxIndex).getLang());
            System.out.println("=====================================================================");

        }
    }
}
