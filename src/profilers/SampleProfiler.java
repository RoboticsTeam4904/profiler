package src.profilers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SampleProfiler {
    // Adapted from
    // https://stackoverflow.com/questions/14915319/get-output-of-terminal-command-using-java?lq=1.

    /**
     * Executes a shell command via {@code java.lang.ProcessBuilder} and returns its
     * output as a String.
     * 
     * @param command The shell command arguments, separated by spaces.
     * @throws IOException Thrown if there are I/O errors communicating with the
     *                     shell.
     * @return The output of the command in {@code String} format.
     */
    private static String executeCommand(ProcessBuilder builder) throws IOException {
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String ret = "";
        String temp = "";
        while ((temp = reader.readLine()) != null) {
            ret += temp + "\n";
        }
        return ret;
    }

    /**
     * Executes the {@code jstack} command via {@code java.lang.ProcessBuilder},
     * formats its output, and returns it as a {@code String[]}.
     * 
     * @param pid The PID of the Java process to profile.
     * @throws IOException Thrown if there is an error retrieving/sending I/O data
     *                     to the shell.
     * @return The list of stack calls under {@code "main"}, with the most nested
     *         calls first.
     */
    public static String[] profile(int pid) {
        try {
            ProcessBuilder builder = new ProcessBuilder("jstack", Integer.toString(pid));
            builder.redirectErrorStream(true);
            String cv = SampleProfiler.executeCommand(builder);
            String[] cvs = cv.split("\n\n", 0)[2].split("\n", 0);
            String[] retcvs = new String[10000]; // max stack trace length is 10000

            int curr_index = 0;
            for (int i = 2; i < cvs.length; i++) {
                if (cvs[i].trim().substring(0, 2).equals("at")) {
                    retcvs[curr_index] = cvs[i].trim().substring(3);
                    curr_index++;
                }
            }

            return Arrays.copyOfRange(retcvs, 0, curr_index);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new String[0];
        }
    }

    /**
     * Executes the {@code jstack} command via {@code java.lang.ProcessBuilder},
     * formats its output, and returns it as a {@code String[]}.
     * 
     * @param pid   The PID of the Java process to profile.
     * @param times How many times to profile the given PID.
     * @throws IOException Thrown if there is an error retrieving/sending I/O data
     *                     to the shell.
     * @return A HashMap containing the call signatures and how often they appeared
     *         in the samples.
     */
    public static Map<String, Integer> profile(int pid, int times) {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 0; i < times; i++) {
            System.out.println(Integer.toString(i) + " of " + Integer.toString(times));
            String[] prof_ret = SampleProfiler.profile(pid);
            for (String sig : prof_ret) {
                if (hm.get(sig) == null) {
                    hm.put(sig, 1);
                } else {
                    hm.put(sig, hm.get(sig) + 1);
                }
            }
        }
        return hm;
    }
}