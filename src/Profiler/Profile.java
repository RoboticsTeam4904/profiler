class Profiler {
    private static String executeCommand(String command) throws Java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(command).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private static profileOnce(int pid) {
        String cv = Profiler.executeCommand("jstack -m " + pid);

    }
}

class CountProfiler {
    

}