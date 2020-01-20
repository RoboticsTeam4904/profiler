import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Profiler {
    // Adapted from
    // https://stackoverflow.com/questions/14915319/get-output-of-terminal-command-using-java?lq=1.
    private static String executeCommand(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String ret = "";
        String temp = "";
        while ((temp = reader.readLine()) != null) {
            ret += temp;
        }
        return ret;
    }

    private static String profileOnce(int pid) {
        try {
            String cv = Profiler.executeCommand("jstack -m " + pid);
            return cv;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}