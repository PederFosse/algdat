package hjelpeklasser;

import hjelpeklasser.Liste;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class EnkeltLenketListe<T> implements Liste<T> {
    /**
     * Hjelpeklasse for EnkeltLenketListe, derfor private
     * Static fordi dens instanser ikke vil være avhengig av å ha kontakt med listeklassens variabler
     * det opprettes da ingen bildinger til den ytre klassen -> enklere kompilert kode
     * Heller ikke aktuelt med subklasser av Node, dermed er den final -> vil også gi fordeler under kompilering
     * @param <T> generisk verdi
     */
    private static final class Node<T> {    // nested class
        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste) {   // konstruktør
            this.verdi = verdi;
            this.neste = neste;
        }
    } // Node

    private Node<T> hode;   // referanse til den første noden i listen
    private Node<T> hale;   // referanse til siste node i listen
    private int antall;     // amtall noder i listen

    public EnkeltLenketListe() {    // standardkonstruktør
        hode = hale = null;    // hode og hale er null
        antall = 0;     // ingen verdier - listen er tom
    }

    public EnkeltLenketListe(T[] a) {
        this(); // alle variabler er nullet

        // Finner første element i a som ikke er null
        int i = 0;
        for(; i < a.length && a[i] == null; i++);

        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null);  // den første noden
            antall = 1; // vi har minst en node

            for (i++; i < a.length; i++) {
                if(a[i] != null) {
                    p = p.neste = new Node<>(a[i], null);   // en ny node
                    antall++;
                }
            }

            hale = p;
        }
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier");

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null);  // tom liste
        } else {
            hale = hale.neste = new Node<>(verdi, null);    // legges bakerst
        }

        antall++;       // en mer i listen
        return true;    // vellykket innlegging
    }

    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier");

        indeksKontroll(indeks, true);

        if (indeks == 0) {  // ny verdi skal ligge først
            hode = new Node<>(verdi, hode); // legges først
            if (antall == 0) {
                hale = hode;    // hode og hale går til samme node
            }
        } else if (indeks == antall) {  // ny verdi skal ligge bakerst
            hale = hale.neste = new Node<>(verdi, null);    // legges bakerst
        } else {
            Node<T> p = hode;   // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) {
                p = p.neste;
            }

            p.neste = new Node<>(verdi, p.neste);   // verdien setttes inn i listen
        }

        antall++;   // listen har fått en ny verdi
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        Node<T> temp = hode;

        for (int i = 0; i < antall; i++) {
            if (temp.verdi.equals(verdi)) return i; // returnerer indeksen til temp om temp.verdi er lik verdi
            temp = temp.neste;  // oppdaterer temp
        }
        return -1;  // ikke funnet
    }

    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier");
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;
        p.verdi = verdi;

        return gammelVerdi;
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        Node<T> q = hode, p = null;

        while (q != null) {
            if (q.verdi.equals(verdi)) break;   // leter etter verdi
            p = q;  // p er forgjengeren til q
            q = q.neste;
        }

        if (q == null) return false;    // verdi finnes ikke
        else if (q == hode) hode = hode.neste;  // hvis q er først, flyttes hode til sin neste
        else p.neste = q.neste; // hopper over q

        if (q == hale) hale = p;    // q var siste node i listen


        q = null;
        p = null;

        antall--;

        return true;
    }

    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T temp;

        if (indeks == 0) {  // skal første verdi fjernes?
            temp = hode.verdi;  // ta vare på verdien som skal fjernes
            hode = hode.neste;  // hode flyttes til neste node
            if (antall == 1) hale = null;   // kun 1 verdi i listen
        } else {
            Node <T> p = finnNode(indeks - 1);  // p er noden foran den som skal fjernes
            Node<T> q = p.neste;    // q skal fjernes
            temp = q.verdi;         // tar vare på verdien som skal fjernes

            if (q == hale) {
                hale = p;   // hvis q er siste node
            }
            p.neste = q.neste;  // "hopper over" q
        }

        antall--;       // reduserer antall
        return temp;    // returnerer fjernet verdi
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public void nullstill() {
        hode = hale = null;
        antall = 0;
    }

    public Iterator<T> iterator() {
        return null;
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Node<T> p = hode; p != null; p = p.neste) {
            sj.add(p.verdi.toString());
        }
        return sj.toString();
    }

    private Node<T> finnNode(int indeks) {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) {
            p = p.neste;
        }
        return p;
    }
}
