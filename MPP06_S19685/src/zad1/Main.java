package zad1;

public class Main {

    public static void main(String[] args) {

        int choice = (int) (Math.random() * 15) + 1;
        System.out.println("Your lucky number is: " + choice);

        Knapsack knapsack = Knapsack.SelectFromFile("plecak.txt", choice);
        knapsack.pack();
    }
}
