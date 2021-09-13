package Forelesninger;

public class F13September {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(myTernaryIfTest(i));
        }
    }

    public static int myTernaryIfTest(int value) {
        return value < 5 ? 0 : 98;
    }
}

