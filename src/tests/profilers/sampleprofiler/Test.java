package src.tests.profilers.sampleprofiler;

import java.util.Scanner;

import src.profilers.SampleProfiler;

public class Test {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);  // Create a Scanner object
        String[] res = SampleProfiler.profile(s.nextInt());

        for(String ln : res) {
            System.out.println(ln);
        }

        s.close();
    }
}