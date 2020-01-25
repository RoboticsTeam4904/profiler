package src.tests.profilers.sampleprofiler;

import java.util.Scanner;
import java.util.Map;
import java.util.Comparator;

import src.profilers.SampleProfiler;

public class Run {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in); // Create a Scanner object
        Map<String, Integer> res = SampleProfiler.profile(s.nextInt(), 100);
        res.entrySet().stream().sorted(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue() - a.getValue();
            }
        }).forEach((Map.Entry<String, Integer> elem) -> {
            System.out.println(Integer.toString(elem.getValue()) + " " + elem.getKey());
        });

        s.close();
    }
}