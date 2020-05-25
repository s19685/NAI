package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Knapsack {

    private List<Item> items;
    private List<Item> packed;
    private int length;
    private int capacity;
    private int finalValue;

    public Knapsack(List<Item> items, int length, int capacity) {
        this.items = items;
        this.length = length;
        this.capacity = capacity;
        finalValue = 0;
    }

    public static Knapsack SelectFromFile(String file, int choice) {
        List<Item> result = new ArrayList<>();
        int length = 0, capacity = 0, i = 0;

        try {
            List<String> lines = Files.readAllLines(Paths.get(file));

            length = Integer.parseInt(lines.get(0).substring(9, 11));
            capacity = Integer.parseInt(lines.get(0).substring(22, 24));

            boolean isFound = false;
            while (!isFound) {
                if (lines.get(i).contains("dataset " + choice)) {
                    isFound = true;
                    String nextline = lines.get(i + 1).replaceAll(" ", "");
                    String scndline = lines.get(i + 2).replaceAll(" ", "");
                    String[] sizes = nextline.substring(7, nextline.lastIndexOf("}")).split(",");
                    String[] values = scndline.substring(6, scndline.lastIndexOf("}")).split(",");

                    System.out.println("sizes " + Arrays.toString(sizes));
                    System.out.println("values " + Arrays.toString(values));

                    for (int j = 0; j < length; j++) {
                        result.add(new Item(j + 1, Integer.parseInt(sizes[j]), Integer.parseInt(values[j])));
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Knapsack(result, length, capacity);
    }

    public void pack() {
        List<Item> result;
        int localSum, localCapa;
        long bound = (long) Math.pow(2, length);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < bound; i++) {
            localCapa = 0;
            localSum = 0;
            result = new ArrayList<>();

            for (int j = 0; j < length; j++) {
                if ((i & (1 << j)) > 0) {
                    localCapa += items.get(j).size;
                    localSum += items.get(j).value;
                    result.add(items.get(j));
                }
            }

            if (localSum > finalValue && localCapa <= capacity) {
                finalValue = localSum;
                packed = result;
                System.out.println(finalValue + " " + localCapa + " " + i);
            }
        }

        long stopTime = System.currentTimeMillis();

        for (Item i : packed) System.out.println(i);
        System.out.println("Ostateczny wynik: " + finalValue);
        System.out.println("czas: " + (stopTime - startTime) / 1000.0 + "s");
    }

    private static class Item {

        private int ID;
        private int size;
        private int value;

        public Item(int ID, int size, int value) {
            this.ID = ID;
            this.size = size;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Item [" + ID + ", " + size + ", " + value + "]";
        }
    }
}
