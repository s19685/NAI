package zad1;

import java.util.ArrayList;
import java.util.List;

class Bayes {

    private int[] DISTINCTS = new int[4];
    private List<double[]> SETOSAs = new ArrayList<>();
    private List<double[]> VIRGINICAs = new ArrayList<>();
    private List<double[]> VERSICOLORs = new ArrayList<>();
    private static final String[] NAMES = {"SETOSA", "VERSICOLOR", "VIRGINICA"};

    Bayes(List<String[]> training) {
        for (int i = 0; i < Main.SIZE; i++) {
            SETOSAs.add(new double[40]);
            VIRGINICAs.add(new double[40]);
            VERSICOLORs.add(new double[40]);
        }

        int index = 0;
        for (String[] line : training) {
            for (int i = 0; i < Main.SIZE; i++) {
                if (line[4].equals("Iris-setosa")) SETOSAs.get(i)[index] = Double.valueOf(line[i].replace(",", "."));
                if (line[4].equals("Iris-virginica")) VIRGINICAs.get(i)[index] = Double.valueOf(line[i].replace(",", "."));
                if (line[4].equals("Iris-versicolor")) VERSICOLORs.get(i)[index] = Double.valueOf(line[i].replace(",", "."));
            }
            index = index == 39 ? 0 : index + 1;
        }

        distinctValues();
    }

    void testing(List<String[]> test) {
        int sum = test.size();
        int[] trues = new int[3];
        int[] falses = new int[3];

        int counter = 0,
                index = 0,
                fakeSetosa = 0,
                fakeSetosa1 = 0,
                fakeVersicolor = 0,
                fakeVirginica = 0;
        for (String[] line : test) {
            double[] viktor = new double[4];
            for (int i = 0; i < viktor.length; i++) viktor[i] = Double.valueOf(line[i].replace(",", "."));
            String verdict = classify(viktor);
            if (verdict.equals(line[4])) counter++;

            if (verdict.equals("Iris-setosa") && index < 10) trues[0]++;
            if (verdict.equals("Iris-setosa") && (index >= 10 && index < 20)) falses[0]++;
            if (verdict.equals("Iris-setosa") && (index >= 20 && index < 30)) falses[0]++;


            if (verdict.equals("Iris-versicolor") && index < 10) fakeSetosa++;
            if (verdict.equals("Iris-versicolor") && (index >= 10 && index < 20)) trues[1]++;
            if (verdict.equals("Iris-versicolor") && (index >= 20 && index < 30)) fakeVersicolor++;

            if (verdict.equals("Iris-virginica") && index < 10) fakeSetosa1++;
            if (verdict.equals("Iris-virginica") && (index >= 10 && index < 20)) fakeVirginica++;
            if (verdict.equals("Iris-virginica") && (index >= 20 && index < 30)) trues[2]++;
//            if (verdict.equals("Iris-setosa") && index < 10) trues[0]++;
//            if (verdict.equals("Iris-setosa") && (index >= 10 && index < 20)) falses[0]++;
//            if (verdict.equals("Iris-setosa") && (index >= 20 && index < 30)) falses[0]++;
//
//
//            if (verdict.equals("Iris-versicolor") && index < 10) falses[1]++;
//            if (verdict.equals("Iris-versicolor") && (index >= 10 && index < 20)) trues[1]++;
//            if (verdict.equals("Iris-versicolor") && (index >= 20 && index < 30)) falses[1]++;
//
//            if (verdict.equals("Iris-virginica") && index < 10) falses[2]++;
//            if (verdict.equals("Iris-virginica") && (index >= 10 && index < 20)) falses[2]++;
//            if (verdict.equals("Iris-virginica") && (index >= 20 && index < 30)) trues[2]++;
            index++;
        }

        double p, r;
//        for (int i = 0; i < NAMES.length; i++) {
//            System.out.println(NAMES[i]);
//            System.out.println("zakwalyfikowane jako: pozytywne | negatywne");
//            System.out.println(i == 0 ? "           pozytywne:      " + trues[i] + "   |     " + (10 - trues[i]) + "" : "           pozytywne:      " + trues[i] + "    |     " + (10 - trues[i]) + "");
//            System.out.println("           negatywne:      " + falses[i] + "    |    " + (20 - falses[i]));
//
//            p = (double) trues[i] / (trues[i] + falses[i]);
//            r = (double) trues[i] / (trues[i] + (10 - trues[i]));
//
//            System.out.println("Precyzja: " + String.format("%.2f", p * 100) + "%");
//            System.out.println("Pelnosc: " + String.format("%.2f", r * 100) + "%");
//            System.out.println("F-miara: " + String.format("%.2f", 2 * p * r / (p + r) * 100) + "%\n");
//        }

        System.out.println("zakwalifikowano jako SETOSA | VERSICOLOR | VIRGINICA");
        System.out.println("SETOSA:                "+trues[0]+"   |      "+falses[0]+"     |    "+ falses[0]);
        System.out.println("VERSICOLOR:             "+fakeSetosa+"   |      "+trues[1]+"     |    "+ fakeVirginica);
        System.out.println("VIRGINICA:              "+fakeSetosa1+"   |      "+fakeVersicolor+"     |    "+ trues[2]);
        double ts = (double) trues[0]+trues[1]+trues[2];
        p = ts/(ts+fakeSetosa+fakeSetosa1+fakeVersicolor);
        r = ts/(ts+falses[0]+fakeVirginica);

        System.out.println("\nDokladnosc: " + String.format("%.2f", 100.0 * counter / sum) + "%");
        System.out.println("Precyzja:   " + String.format("%.2f", p * 100) + "%");
        System.out.println("Pelnosc:    " + String.format("%.2f", r * 100) + "%");
        System.out.println("F-miara:    " + String.format("%.2f", 2 * p * r / (p + r) * 100) + "%\n");



    }

    String classify(double[] vector) {
        double setosa = countProbability(vector, "Iris-setosa");
        double versicolor = countProbability(vector, "Iris-versicolor");
        double virginica = countProbability(vector, "Iris-virginica");
        System.out.println("setosa: " + setosa);
        System.out.println("versicolor: " + versicolor);
        System.out.println("virginica: " + virginica);
        System.out.println("-------------------------------");
        double max = Math.max(setosa, Math.max(virginica, versicolor));

        return max == setosa ? "Iris-setosa" : max == virginica ? "Iris-virginica" : "Iris-versicolor";
    }


    private double countProbability(double[] vector, String decision) {
        double result = 1.0;
        int all = 40;
        System.out.println(decision);
        for (int i = 0; i < vector.length; i++) {
            result *= howMany(vector[i], findBy(decision).get(i)) / all;
        }
        System.out.println("przed wygladzaniem: " + result / 3);
        if (result == 0.0) {
            result = 1.0;
            for (int i = 0; i < vector.length; i++) {
                result *= (1 + howMany(vector[i], findBy(decision).get(i))) / (all + DISTINCTS[i]);
            }
        } else {
            result = (1 + howMany(vector[0], findBy(decision).get(0))) / (all + DISTINCTS[0]);
            for (int i = 1; i < vector.length; i++) {
                result *= howMany(vector[i], findBy(decision).get(i)) / all;
            }
        }
        System.out.println("po wygladzaniu:     " + result / 3 + "\n");

        return result / 3;
    }

    private void distinctValues() {
        List<List<Double>> ds = new ArrayList();
        for (int i = 0; i < DISTINCTS.length; i++) {
            ds.add(new ArrayList<>());
            for (double d : SETOSAs.get(i) ) if(!ds.get(i).contains(d)) ds.get(i).add(d);
            for (double d : VERSICOLORs.get(i) ) if(!ds.get(i).contains(d)) ds.get(i).add(d);
            for (double d : VIRGINICAs.get(i) ) if(!ds.get(i).contains(d)) ds.get(i).add(d);
            DISTINCTS[i] = ds.get(i).size();
            System.out.println(DISTINCTS[i]);
        }
    }

    private double howMany(double d, double[] from) {
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
