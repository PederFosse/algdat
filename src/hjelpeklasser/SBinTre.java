package hjelpeklasser;

import eksempelklasser.Person;

import java.util.Comparator;

public class SBinTre<T> {
    private static final class Node<T> {
        private T verdi;    // nodens verdi
        private Node<T> venstre, høyre;     /// venstre og høyre barn

        private Node(T verdi, Node<T> v, Node<T> h) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    } // class Node

    private Node<T> rot;
    private int antall;
    private final Comparator<? super T> comp;

    public SBinTre(Comparator<? super T> c) {
        rot = null;
        antall = 0;
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> sbintre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public static void main(String[] args) {
        SBinTre<Person> tre1 = SBinTre.sbintre();          // 1. konstruksjonsmetode

        Comparator<Person> c = Comparator.naturalOrder();
        SBinTre<Person> tre2 = SBinTre.sbintre(c);         // 2. konstruksjonsmetode

        System.out.println(tre1.antall() + " " + tre2.antall());
    }
} // class SBinTre
