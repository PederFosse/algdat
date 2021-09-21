package Forelesninger.F16September;

import hjelpeklasser.Tabell;

import java.util.Arrays;

public class GenericSort {

    public static void main(String[] args) {
        // int => datatype, ikke klasse
        // Integer => java-klasse som implementerer Comparable
        Integer[] values = {1, 6, 3, 7, 9, 8, 4, 5, 2};
        Character[] charValues = {'b', 'a', 'c', 'd', 'e', 'f'};
        Person[] people = {new Person("peder", "a"), new Person("fredrik", "a"), new Person("rikard", "a")};

        System.out.println(Arrays.toString(values));
        sort(values);
        System.out.println(Arrays.toString(values));

        System.out.println();

        System.out.println(Arrays.toString(charValues));
        sort(charValues);
        System.out.println(Arrays.toString(charValues));

        System.out.println();

        System.out.println(Arrays.toString(people));
        sort(people);
        System.out.println(Arrays.toString(people));

    }

    public static class Person implements Comparable<Person> {
        String fName;
        String lName;

        public Person(String fName, String lName) {
            this.fName = fName;
            this.lName = lName;
        }

        public int compareTo(Person other) {
            int lastNameCompare = this.lName.compareTo(other.lName);
            if (lastNameCompare == 0) { // samme etternavn, sammenlikn fornavn
                return this.fName.compareTo(other.fName);
            } else {    // forskjellig etternavn, sammenlikn etternavn
                return lastNameCompare;
            }
        }

        public String toString() {
            return this.lName + " " + this.fName;
        }
    }

    public static
    <T extends Comparable<? super T>>
    void sort(T[] values) {
        // vi skal sortere n-1 tall, da er siste tall sortert riktig
        for (int i = 0; i < values.length - 1; i++) {
            // Finn indeks til største tall i intervallet [i, n)
            int maxIndex = maxValue(values, i, values.length);

            T temp = values[maxIndex];
            values[maxIndex] = values[i];
            values[i] = temp;
        }
    }

    /**
     * Denne funksjonen finner indeksen til største heltall innenfor intervallet [begin, end)
     * Vi bruker funksjonen compareTo som ligger i Comparable interfacet.
     * a.compareTo(b) > 0 <=> ( a > b) == true
     * a.compareTo(b) < 0 <=> (a < b) == true
     * a.compareTo(b) == 0 <=> (a == b) == true
     * @param values
     * @param begin
     * @param end
     * @param <T>
     * @return
     */
    public  // Denne funksjonen synes for hele verden. Alt. private
    static  // Trenger ikke å instansiere klassen for å bryke denne funksjonen
    <T extends Comparable<? super T>>   // Denne funksjonen bruker T som en generisk type
    int     // Returnerer et heltall => indeksen der vi har største verdi
    maxValue(T[] values, int begin, int end) {
        int currentMaxIndex = begin;
        T currentMax = values[begin];

        for (int i = begin; i < end; i++) {
            if (currentMax.compareTo(values[i]) < 0) {
                currentMaxIndex = i;
                currentMax = values[i];
            }
        }

        return currentMaxIndex;
    }
}
