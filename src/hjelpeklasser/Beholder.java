package hjelpeklasser;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;


public interface Beholder<T> extends Iterable<T> {
    boolean leggInn(T verdi);       // legger inn i beholderen
    boolean inneholder(T verdi);    // sjekker om den inneholder verdi
    boolean fjern(T verdi);         // fjerner verdi fra beholderen
    int antall();                   // returnerer antallet i beholderen
    boolean tom();                  // sjekker om beholderen er tom
    void nullstill();               // tømmer beholderen
    Iterator<T> iterator();         // returnerer en iterator, strengt tatt unødvendig fordi denne arves av Iterable

    default boolean fjernHvis(Predicate<? super T> p) { // betingelsesfjerning
        Objects.requireNonNull(p);  // kaster unntak

        boolean fjernet = false;
        for (Iterator<T> i = iterator(); i.hasNext(); ) {
            if (p.test(i.next())) { // betingelsen
                i.remove();
                fjernet = true;     // fjerner
            }
        }
        return fjernet;
    }
}   // Beholder
