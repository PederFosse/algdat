package uke34;

public class S113o6 {
    public static void main(String[] args) {
        System.out.println(fak(6));
    }

    public static long fak(int n) {
        if (n > 1) {
            return n * fak(n-1);
        } else {
            return 1;
        }
    }
}
