package uke34;

public class S115o1 {
    // sjekk at metoden maks gir korrekte resultater, hva skjer hvis a har lengde 1 og hva hvis a er tom?

    public static void main(String[] args) {
        int[] liste1 = {1, 2, 5, 3};
        int[] liste2 = {4};
        int[] liste3 = {};

        System.out.println(maks(liste1)); // fungerer som det skal, returnerer index 2
        System.out.println(maks(liste2)); // funker som det skal, returnerer index 0
        System.out.println(maks(liste3)); // ArrayIndexOutOfBoundsError
    }

    public static int maks(int[] a) {
        int sist = a.length - 1;
        int m = 0;
        int maksverdi = a[0];
        int temp = a[sist];
        a[sist] = 0x7fffffff; // legger tallet 2147483647 som siste tall

        for (int i = 0; ; i++) { // i starter med 0
            if (a[i] >= maksverdi) {
                if (i == sist) {
                    a[sist] = temp;
                    return temp >= maksverdi ? sist : m;
                } else {
                    maksverdi = a[i];
                    m = i;
                }
            }
        }
    }
}
