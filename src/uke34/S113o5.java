package uke34;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class S113o5 {
    public static void main(String[] args){
        // Tester oppgaven med en enkel tabell
        int[] testArray = {34, 4, 13, 33, 8, 2, 9};
        System.out.println(Arrays.toString(minmax(testArray)));
    }

    public static int[] minmax(int[] a) { // returner posisjonen til det største og minste tallet i en int[]
        if (a.length < 1) throw new NoSuchElementException("Tabellen er tom!");

        int firstNum = a[0];
        int minIndex = 0;
        int maxIndex = 0;
        int min = firstNum;
        int max = firstNum;

        int counter = 0;

        for (int i = 1; i < a.length; i++) {
            int newNumber = a[i];
            if (newNumber < min) {
                minIndex = i;
                min = newNumber;
            } else if (newNumber > max){
                maxIndex = i;
                max = newNumber;
            }
        }
        System.out.println(counter + " antall sammenlikninger.");
        int[] returnList = {minIndex, maxIndex};
        return returnList;
    }
}
