package Forelesninger;

public class GenericsTest {
    public static void main(String[] args) {
        Integer[] a = {1, 43, 6, 13, 8};
        Character[] b = {'a', 'c', 'z', 'k', 'l'};
        String[] c = {"Finn", "Fredrik", "Peder", "Rikard"};
        Person[] d = {new Person("Finn", "Finnstad"), new Person("Peder", "Fosse"), new Person("Rikard", "Rikardsen")};

        System.out.println("Indeks til maks verdi i a:");
        System.out.println(maks(a));

        System.out.println("\nIndeks til maks verdi i b:");
        System.out.println(maks(b));

        System.out.println("\nIndeks til maks verdi i c:");
        System.out.println(maks(c));

        System.out.println("\nIndeks til maks i person-array");
        System.out.println(maks(d));

    }

    static <T extends Comparable<? super T>> int maks(T[] values) {
        T currentMax = values[0];
        int currentMaxIndex = 0;

        for (int i = 1; i < values.length; i++) {
            if (values[i].compareTo(currentMax) > 0) {
                currentMax = values[i];
                currentMaxIndex = i;
            }
        }

        return currentMaxIndex;
    }

    public static class Person implements Comparable<Person>{
        String firstName;
        String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public int compareTo(Person other) {
            int last_compare = this.lastName.compareTo(other.lastName);
            if (last_compare == 0) {
                return this.firstName.compareTo(other.firstName);
            } else {
                return last_compare;
            }
        }
    }
}
