package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knapsack {

    private List<Item> items;
    private int length;
    private int capacity;

    public Knapsack(List<Item> items, int length, int capacity) {
        this.items = items;
        this.length = length;
        this.capacity = capacity;
    }

    public static Knapsack SelectFromFile(String file, int choice) {

        List<Item> result = new ArrayList<>();
        int length = 0, capacity = 0;

        try {
            List<String> lines = Files.readAllLines(Paths.get(file));

            length = Integer.parseInt(lines.get(0).substring(9, 11));
            capacity = Integer.parseInt(lines.get(0).substring(22, 24));


            int i = 0;
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

    public void pack(){

        long startTime = System.currentTimeMillis();

        for (Item item : items) {
            System.out.println(item);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        long stopTime = System.currentTimeMillis();


        String time;
        System.out.println("czas: "+(stopTime-startTime)/1000.0+"s");

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
