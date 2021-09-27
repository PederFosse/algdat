package eksempelklasser;

import hjelpeklasser.Tabell;

import java.util.Arrays;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private final String fornavn;
    private final String etternavn;

    public Person(String fornavn, String etternavn) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public String fornavn() { return fornavn; };
    public String etternavn() { return etternavn; };

    public int compareTo(Person p) {    // pga. Comaparable<Person>
        int cmp = etternavn.compareTo(p.etternavn); // etternavn
        if (cmp != 0 ) {    // er etternavn ulike?
            return cmp;
        }
        return fornavn.compareTo(p.fornavn);    // sammenlikner fornavn
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }

        return compareTo((Person) o) == 0;
    }

    public int hashCode() {
        return Objects.hash(etternavn, fornavn);
    }

    public String toString() {
        return fornavn + " " + etternavn;
    }

    public static void main(String[] args) {
        Heltall x = new Heltall(3), y = new Heltall(3); // x og y er like
        System.out.println(x.hashCode() + " " + y.hashCode());
    }
} // class Person
