package zad1;

import java.util.ArrayList;
import java.util.List;

public class Bayes {

    private List<String[]> versicolor;
    private List<String[]> virginica;
    private List<String[]> setosa;
    private int dataSize;
    private List<String[]> training;

    private static double[] x1;
    private static double[] x2;
    private static double[] x3;
    private static double[] x4;

    private List<double[]> vectors;

    public Bayes(List<String[]> training ,List<String[]> test ){
        this.training = training;
        vectors = new ArrayList<>();
        setosa = new ArrayList<>();
        virginica = new ArrayList<>();
        versicolor = new ArrayList<>();
        x1 = new double[training.size()];
        x2 = new double[training.size()];
        x3 = new double[training.size()];
        x4 = new double[training.size()];
        dataSize = training.size();
        for (int i = 0; i <dataSize ; i++) {

            x1[i] = Double.valueOf(training.get(i)[0].replace(",","."));
            x2[i] = Double.valueOf(training.get(i)[1].replace(",","."));
            x3[i] = Double.valueOf(training.get(i)[2].replace(",","."));
            x4[i] = Double.valueOf(training.get(i)[3].replace(",","."));
            vectors.add(new double[]{x1[i], x2[i], x3[i], x4[i]});
            if(training.get(i)[4].equals("Iris-setosa")) setosa.add(training.get(i));
            if(training.get(i)[4].equals("Iris-virginica")) virginica.add(training.get(i));
            if(training.get(i)[4].equals("Iris-versicolor")) versicolor.add(training.get(i));
        }
        int it = 0;
        for (double[] vector : vectors ) {
            System.out.println(countProbability(vector,training.get(it)[4]));
            it++;
        }
    }


    public static int findAllfor(double value, int column){
        int result = 0;

        switch (column){
            case 0:
                for (double d: x1 ) if(d == value) result++;
                break;
            case 1:
                for (double d: x2 ) if(d == value) result++;
                break;
            case 2:
                for (double d: x3 ) if(d == value) result++;
                break;
            case 3:
                for (double d: x4 ) if(d == value) result++;
                break;
        }

        return result;
    }

    public double countProbability(double[] vector, String decision){
        double result =0.0;

        int all = findBy(decision).size();
        for (int i =0; i<vector.length; i++) {
            result *= findfor(vector[i],i,findBy(decision))/all;
        }
        System.out.println(result);
        result *= 0.3333;
        return result;
    }

    private double findfor(double d,int index, List<String[]> from) {
        double result =0.0;
        for (String[] line : from) {
            if(Double.valueOf(line[index].replace(",",".")) == d) result++;
        }
        return result;

    }

    private List<String[]> findBy(String id) {
        if(id.equals("Iris-setosa")) return setosa;
        if(id.equals("Iris-virginica")) return virginica;
        if(id.equals("Iris-versicolor")) return versicolor;
        return null;
    }


    @Override
    public String toString() {
        return "setosa = "+setosa + " virginica = "+ virginica + " versicolor = "+ versicolor;
    }
}
