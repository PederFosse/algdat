package hjelpeklasser;

import java.util.*;
import java.util.function.Predicate;

public class TabellListe<T> implements Liste<T> {
    private T[] a;
    private int antall;

    public TabellListe(T[] b) {
        this(b.length); // den andre konstruktøren
        for (T verdi : b) {
            if (verdi != null) {
                a[antall++] = verdi;    // hopper over null-verdier
            }
        }
    }

    @SuppressWarnings("unchecked")
    public TabellListe(int storrelse) {
        a = (T[]) new Object[storrelse];    // oppretter tabellen
        antall = 0; // foreløpig ingen verdier
    }

    public TabellListe() {  // standardkonstruktør
        this(10);   // størrelse på 10
    }

    public int antall() {
        return antall;  // returnerer antallet
    }

    public boolean tom() {
        return antall == 0; // listen er tom hvis antall er 0
    }

    @SuppressWarnings("unchecked")
    public void nullstill() {
        if (a.length > 10) {
            a = (T[])new Object[10];
        } else {
            for (int i = 0; i < antall; i++) {
                a[i] = null;
            }
        }

        antall = 0;
    }

    private class TabellListeIterator implements Iterator<T> {
        private int denne = 0;  // instansvariabel
        private boolean fjernOK = false;

        public boolean hasNext() {  // sjekker om det er fler igjen
            return denne < antall;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Tomt eller ingen verdier igjen");
            }
            fjernOK = true;
            return a[denne++];  // a[denne] returneres før denne++
        }

        public void remove() {  // ny versjon
            if (!fjernOK) {
                throw new IllegalStateException("Ulovlig tilstand");
            }
            fjernOK = false;    // remove() kan ikke kalles på nytt

            // verdien i denne-1 skal fjernes da den ble returnert i siste kall
            // på next(), verdiene fra og med denne flyttes derfor en mot venstre

            antall--;
            denne--;

            System.arraycopy(a, denne + 1, a, denne, antall - denne);   // tetter igjen
            a[antall] = null;   // verdien som lå lengst til høyre nulles
        }
    } // TabellListeIterator

    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }

    public T hent(int indeks) {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig
        return a[indeks];   // returnerer et tabellelement
    }

    public int indeksTil(T verdi) {
        for (int i = 0; i < antall; i++) {
            if (a[i].equals(verdi)) {
                return i;   // funnet!
            }
        }
        return -1;  // ikke funnet
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");

        for (int i = 0; i < antall; i++) {
            sj.add(a[i].toString());
        }

        return sj.toString();
    }

    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null-verdier ulovlig");
        indeksKontroll(indeks, false);

        T gammelverdi = a[indeks];  // tar vare på den gamle verdien
        a[indeks] = verdi;          // legger inn ny verdi
        return gammelverdi;         // returnerer gammel verdi
    }

    public boolean fjern(T verdi) {
        int indeks = indeksTil(verdi);
        if (indeks < 0) return false;   // ikke funnet

        fjern(indeks);
        return true;
    }

    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
        T verdi = a[indeks];

        antall--;   // sletter ved å flytte verdier mot venstre
        System.arraycopy(a, indeks + 1, a, indeks, antall - indeks);
        a[antall] = null;  // tilrettelegger for "søppeltømming"

        return verdi;
    }

    public boolean leggInn(T verdi) {   // legg inn bakerst
        Objects.requireNonNull(verdi, "null-verdi er ulovlig");

        if (antall == a.length) {   // en full tabell utvides med 50%
            a = Arrays.copyOf(a, (3*antall) / 2 + 1);
        }

        a[antall++] = verdi;    // setter inn ny verdi
        return true;            // vellykket innlegging
    }

    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null-verdi er ulovlig");
        indeksKontroll(indeks, true);   // true: indeks = antall er lovlig

        if (antall == a.length) {   // full tabell utvides med 50%
            a = Arrays.copyOf(a, (3*antall) / 2 + 1);
        }

        // rydder plass til ny verdi
        System.arraycopy(a, indeks, a, indeks + 1, antall - indeks);

        a[indeks] = verdi;  // setter inn ny verdi
        antall++;           // vellykket innlegging
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public boolean fjernHvis(Predicate<? super T> p) {  // betingelsesfjerning
        Objects.requireNonNull(p);  // kaster unntak om p er null
        int nyttAntall = antall;

        for (int i = 0, j = 0; j < antall; j++) {
            if (p.test(a[j])) nyttAntall--; // a[j] skal fjernes
            else a[i++] = a[j];    // forskyver
        }

        for (int i = nyttAntall; i < antall; i++) {
            a[i] = null;    // tilrettelegger for "søppeltømming";
        }

        boolean fjernet = nyttAntall < antall;

        antall = nyttAntall;
        return fjernet;
    }

    public static void main(String... args) // neste oppgave er oppgave 3.2.3.4
    {
        String[] s = {"Jens","Per","Kari","Ole","Berit","Jens","Anne","Nils","Siv"};

        Liste<String> liste = new TabellListe<>();


        for (String navn : s) liste.leggInn(0,navn);  // legger inn først

        System.out.println("Vi henter " + liste.hent(5) + ".");

        System.out.println("Nils er på plass " + liste.indeksTil("Nils") + "!");

        liste.oppdater(2,"Anna");  // bytter ut Anne med Anna på plass 2

        System.out.println(liste.fjern(0) + " er slettet!");

        System.out.println("Listeinnhold: " + liste);

        liste.fjernHvis(x -> x.equals("Jens"));  // fjerner alle forekomster av Jens

        liste.forEach(x -> System.out.print(x + " "));
    }
} // TabellListe
