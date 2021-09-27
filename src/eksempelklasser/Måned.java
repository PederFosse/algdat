package eksempelklasser;

import java.util.Arrays;

public enum Måned {
    JAN ("Januar", 1),
    FEB ("Februar", 2),
    MAR ("Mars", 3),
    APR ("April", 4),
    MAI ("Mai", 5),
    JUN ("Juni", 6),
    JUL ("Julie", 7),
    AUG ("August", 8),
    SEP ("September", 9),
    OKT ("Oktober", 10),
    NOV ("November", 11),
    DES ("Desember", 12);

    private final String fulltnavn;
    private final int mndnr;

    private Måned (String fulltnavn, int mndnr) {
        this.fulltnavn = fulltnavn;
        this.mndnr = mndnr;
    }

    public int mndnr() {
        return mndnr;
    }

    @Override
    public String toString() {
        return fulltnavn;
    }

    public String toString(int mnd) {
        if (mnd < 1 || mnd > 12) {
            throw new IllegalArgumentException("Ulovlig månedsnummer");
        }
        return values()[mnd - 1].toString();
    }

    public static Måned[] vår() {
        return Arrays.copyOfRange(values(), 3, 5);
    }

    public static Måned[] sommer() {
        return Arrays.copyOfRange(values(), 5, 8);
    }

    public static Måned[] høst() {
        return Arrays.copyOfRange(values(), 8, 10);
    }

    public static Måned[] vinter() {
        return new Måned[] {NOV, DES, JAN, FEB, MAR};
    }

    public static void main(String[] args) {
        for (Måned m : Måned.vinter()) {
            System.out.println(m.toString() + " (" + m.name() + ") " + m.mndnr);
        }
    }
} // enum Måned
