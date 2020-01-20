package tests.examples;

class ForLoop {
    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            ForLoop.printOut(i);
        }
    }

    public static void printOut(int i) {
        if (i % 15 == 0) {
            System.out.println("Fizzbuzz");
        } else if (i % 5 == 0) {
            System.out.println("Buzz");
        } else if (i % 3 == 0) {
            System.out.println("Fizz");
        } else {
            System.out.println(i);
        }
    }
}