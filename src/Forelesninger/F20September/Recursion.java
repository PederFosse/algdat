package Forelesninger.F20September;

public class Recursion {
    public static void main(String[] args) {
        int value = 5;
        System.out.println(factorial(value));
    }

    public static int factorial(int n) {
        if (n > 1) {
            return n * factorial(n - 1 );
        }
        return 1;
    }
}
