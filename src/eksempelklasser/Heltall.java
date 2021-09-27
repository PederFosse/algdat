package eksempelklasser;

public final class Heltall implements Comparable<Heltall> {
    private final int verdi;        // et heltall som instansvariabel

    public Heltall(int verdi) {     // konstruktør
        this.verdi = verdi;
    }

    public int intVerdi() { return verdi; };

    public int compareTo(Heltall h) {   // Heltall som parameter
        return verdi < h.verdi ? -1 : (verdi == h.verdi ? 0 : 1);
    }

    public boolean equals(Object o) {
        if (o == this) {    // sammenlikner med seg selv
            return true;
        }
        if (!(o instanceof Heltall)) {  // feil datatype
            return false;
        }
        return verdi == ((Heltall)o).verdi;
    }

    public boolean equals(Heltall h) {
        return verdi == h.verdi;
    }

    public int hashCode() {
        return 31 + verdi;
    }

    public String toString() {
        return Integer.toString(verdi);
    }
} // class heltall
