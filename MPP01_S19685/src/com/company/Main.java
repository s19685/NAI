package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static List<String[]> TRAINING_SET;
    private static List<String[]> TEST_SET;
    private static int COUNTER;

    public static void main(String[] args) {

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

        showList(TRAINING_SET);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w kNN, podaj k:");

        int k = scanner.nextInt();
        int vectorSize = TRAINING_SET.get(0).length - 1;

        COUNTER = 0;
        for (String[] testString : TEST_SET) {
            Double[] array = new Double[testString.length - 1];
            for (int i = 0; i < array.length; i++) array[i] = Double.parseDouble(testString[i].replace(",", "."));

            String result = getNearest(array, k);
            System.out.println(testString[testString.length - 1].equals(result) ? show(testString) : showErr(testString, result));
        }
        System.out.println("Prawidlowo zakwalifikowane przyklady: " + COUNTER);

        System.out.println("Podaj wektor skladajacy sie z " + vectorSize + " argumentow.");

        Double[] arguments = new Double[vectorSize];
        for (int i = 0; i < vectorSize; i++) {
            System.out.println("podaj " + (i + 1) + " argument");
            arguments[i] = scanner.nextDouble();
        }

        System.out.println("Podany wektor zostal zaklasyfikowany jako " + getNearest(arguments, k));

    }


    private static String getNearest(Double[] array, int k) {
        List<Result> resultList = new ArrayList<>();

        for (String[] leaf : TRAINING_SET) {
            double distance = 0.0;
            for (int i = 0; i < leaf.length - 1; i++) {
                distance += Math.pow(Double.parseDouble(leaf[i].replace(",", ".")) - array[i], 2);
            }
            resultList.add(new Result(Math.sqrt(distance), leaf[leaf.length - 1]));
        }

        resultList.sort(Comparator.comparingDouble(Result::getDistance));

        String[] types = new String[k];
        for (int i = 0; i < k; i++) {
            types[i] = resultList.get(i).getType();
        }

        return majorityType(types);
    }

    private static String majorityType(String[] tab) {
        Set<String> h = new HashSet<>(Arrays.asList(tab));
        String[] unique = h.toArray(new String[0]);
        int[] counts = new int[unique.length];
        for (int i = 0; i < unique.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                if (tab[j].equals(unique[i])) counts[i]++;
            }
        }

//        for (int i = 0; i < unique.length; i++) System.out.println(unique[i]);

//        for (int i = 0; i < counts.length; i++) System.out.println(counts[i]);


        int max = counts[0];

        for (int i = 1; i < counts.length; i++){
            if (counts[i] > max) max = counts[i];
        }

        int freq = 0;
        for(int i=0;i<counts.length;i++){
            if(counts[i] == max) freq++;
        }

        int index = -1;

        if(freq==1){
            for(int i=0; i<counts.length; i++){
                if(counts[i] == max){
                    index = i;
                    break;
                }
            }
            return unique[index];
        }
        else{
            int[] ix = new int[freq];
            System.out.println("multiple majority classes: "+freq);
            int ixi = 0;
            for (int i = 0; i<counts.length;i++){
                if(counts[i] == max){
                    ix[ixi] = i;
                    ixi++;
                }
            }
            for (int i = 0; i<ix.length;i++) System.out.println("class index"+ ix[i]);

            int randomIndex = new Random().nextInt(ix.length);
            System.out.println("random index "+randomIndex);
            int nIndex = ix[randomIndex];
            return unique[nIndex];
        }
    }


    private static void showList(List<String[]> list) {
        for (String[] s : list) {
            System.out.print("[ ");
            for (String string : s) System.out.print(string + " ");
            System.out.println("]");
        }
    }

    private static String showErr(String[] tab, String correct) {
        String result = "";
        for (String s : tab) result += "\u001B[31m" + s + " ";
        result += "\u001B[0m " + correct;
        return result;
    }

    private static String show(String[] tab) {
        COUNTER++;
        String result = "";
        for (String s : tab) result += s + " ";
        return result;
    }


}
