package hjelpeklasser;

import java.util.Iterator;

public abstract class AbstraktBeholder<T> implements Beholder<T> {
    public abstract boolean leggInn(T t);   // en abstrakt metode

    public boolean inneholder(T t) {

        if (t == null) {    // tillatt med null-verdier
            for (T value : this) {      // for each løkke
                if (value == null) {    // sjekker om verdien er null
                    return true;        // t finnes
                }
            }
        }

        for (T value : this) {      // for each løkke
            if (value.equals(t)) {  // sammenlikner
                return true;        // t finnes
            }
        }
        return false;   // t finnes ikke
    }

    public boolean fjern(T t) {
        Iterator<T> i = iterator();

        if (t == null) {    // fjerner en eventuell null-verdi
            while (i.hasNext()) {   // flere elementer igjen
                if (i.next() == null) { // sammenlikner
                    i.remove();     // fjerner
                    return true;    // vellykket fjerning
                }
            }
        } else {    // t er ikke lik null
            while(i.hasNext()) {    // flere elementer igjen
                if (t.equals(i.next())) {   // sammenlikner
                    i.remove();     // fjerner
                    return true;    // vellykket fjerning
                }
            }
        }
        return false;   // mislykket fjerning
    }

    public int antall() {
        int antall = 0;
        for (T t : this) {
            antall++;
        }
        return antall;
    }

    public boolean tom() {
        return antall() == 0;   // ferdig kode
    }

    public void nullstill() {
        // foreløpig kode
    }

    public abstract Iterator<T> iterator();     // en abstrakt metode

    public String toString() {
        return null;    // foreløpig kode
    }
}
