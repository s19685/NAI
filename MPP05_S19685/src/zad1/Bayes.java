package zad1;

import java.util.ArrayList;
import java.util.List;

public class Bayes {

    private List<double[]> SETOSAs = new ArrayList<>();
    private List<double[]> VIRGINICAs = new ArrayList<>();
    private List<double[]> VERSICOLORs = new ArrayList<>();

    public Bayes(List<String[]> training) {
        for (int i = 0; i < Main.SIZE; i++) {
            SETOSAs.add(new double[40]);
            VIRGINICAs.add(new double[40]);
            VERSICOLORs.add(new double[40]);
        }

        int index = 0;
        for (String[] line : training) {
            for (int i = 0; i < Main.SIZE; i++) {
                if(line[4].equals("Iris-setosa")) SETOSAs.get(i)[index] = Double.valueOf(line[i].replace(",", "."));
                if(line[4].equals("Iris-virginica")) VIRGINICAs.get(i)[index] = Double.valueOf(line[i].replace(",","."));
                if(line[4].equals("Iris-versicolor")) VERSICOLORs.get(i)[index] = Double.valueOf(line[i].replace(",","."));
            }
            index = index == 39 ? 0 : index + 1;
        }
    }

    public void testing(List<String[]> test) {
        int sum = test.size();
        int counter = 0,
            trueSetosa = 0,
            trueVirgin = 0,
            trueVersi = 0,
            index = 0;
        for (String[] line : test ) {
            double[] viktor = new double[4];
            for (int i = 0; i < viktor.length; i++) viktor[i] = Double.valueOf(line[i].replace(",","."));
            String verdict = classify(viktor);
            if(verdict.equals(line[4])) counter++;
            if(verdict.equals("Iris-setosa") && index<10) trueSetosa++;
            if(verdict.equals("Iris-versicolor") && (index>=10 && index<20)) trueVersi++;
            if(verdict.equals("Iris-virginica") && (index>=20 && index<30)) trueVirgin++;
            index++;
        }

        System.out.println("\n        poprawnie | niepoprawnie");
        System.out.println("SETOSA:     "+100*trueSetosa/10.0 + "%| "+100*(10-trueSetosa)/10.0+"%");
        System.out.println("VIRGINICA:  "+100*trueVirgin/10.0 +"% | "+100*(10-trueVirgin)/10.0+"%");
        System.out.println("VERSICOLOR: "+100*trueVersi/10.0 + "% | "+100*(10-trueVersi)/10.0+"%");
        //TODO some measure results
        System.out.println("Poprawnie zaklasyfikowano "+String.format("%.2f",100.0*counter/sum)+"%");
    }

    public String classify(double[] vector) {
        double setosa = countProbability(vector, "Iris-setosa");
        double virginica = countProbability(vector, "Iris-virginica");
        double versicolor = countProbability(vector, "Iris-versicolor");
        System.out.println("\nsetosa: " + setosa);
        System.out.println("virginica: " + virginica);
        System.out.println("versicolor: " + versicolor);
        double max = Math.max(setosa, Math.max(virginica, versicolor));

        return max == setosa ? "Iris-setosa" : max == virginica ? "Iris-virginica" : "Iris-versicolor";
    }


    public double countProbability(double[] vector, String decision) {
        double result = 1.0;
        int all = 40;
        System.out.println(decision + " przed wygladzaniem");
        for (int i = 0; i < vector.length; i++) {
            result *= howMany(vector[i], findBy(decision).get(i)) / all;
            System.out.println(result);
        }
        if (result == 0.0) {
            System.out.println("po wygladzaniu");
            result = 1.0;
            for (int i = 0; i < vector.length; i++) {
                result *= (1 + howMany(vector[i], findBy(decision).get(i))) / (all + 3);
                System.out.println(result);
            }
        }
        else{
            System.out.println("po wygladzaniu pierwszego argumentu");
            result = (1 + howMany(vector[0], findBy(decision).get(0))) / (all + 3);
            for (int i = 1; i < vector.length; i++) {
                result *= howMany(vector[i], findBy(decision).get(i)) / all;
                System.out.println(result);
            }
        }
        System.out.println("probability: "+result*all/120+"\n");
        return result / 3;
    }

    public double howMany(double d, double[] from) {
        double result = 0.0;
        for (double ds : from) if (d == ds) result++;
        return result;
    }

    private List<double[]> findBy(String id) {
        if (id.equals("Iris-setosa")) return SETOSAs;
        if (id.equals("Iris-virginica")) return VIRGINICAs;
        if (id.equals("Iris-versicolor")) return VERSICOLORs;
        return null;
    }

}
