package obligOppgaver;

////// Løsningsforslag Oblig 1 ////////////////////////

import java.util.NoSuchElementException;

/**
 * Oblig 1 i algoritmer og datastrukturer
 */
public class Oblig1 {
    private Oblig1() {}

    ///// Oppgave 1 /////////////////////////////////////
    /**
     * Oppgave 1: Finner og returnerer den største verdien i tabellen
     * ved å sammenlikne et tall og dens nabo og bytte plass slik at det største tallet
     * kommer lengst til høyre i tabellen (sortere tabellen in place) og returnere det siste tallet i denne tabellen
     *
     *
     * Teorispørsmål:
     * 1. Når blir det flest ombyttinger?
     *  I enhver situasjon der det største tallet ligger på index 0.
     *
     * 2. Når blir det færrest ombyttinger?
     *  Når tallene ligger i stigende rekkefølge
     *
     * 3. Hvor mange blir det i gjennomsnitt?
     *  n / 2
     *
     *
     * @param a en heltallstabell (int[])
     * @return den største verdien i tabellen (int)
     */
    public static int maks(int[] a) {
        if (a.length == 0) {       // Sjekker om tabell er tom
            throw new NoSuchElementException("values.length (" + a.length + ") er null!");
        } else if (a.length == 1) {
            return a[0];           // returnerer eneste elementet i listen dersom listen kun har ett element
        }

        flyttStørstBakerst(a);

        return a[a.length - 1]; // returnerer siste element i listen da det er størst.
    }

    /**
     * Teller opp antall ombyttinger som må utføres dersom man kaller på flyttStørstBakerst
     * @param a heltallsarray
     * @return antall ombyttinger
     */
    public static int ombyttinger(int[] a) {
        int counter = 0;
        int retract = 0;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i - retract] > a[i+1]) { // trekker retract fra i for å simulere et bytt i tabellen
                counter++;      // inkrementerer telleren dersom det skal gjøres et bytte her
                retract++;
            } else {
                retract = 0;    // re-setter retract til null dersom et bytt ikke ble utført
            }
        }
        return counter;
    }

    /**
     * Bobler den største verdien i tabellen bakerst
     * @param a
     */
    public static void flyttStørstBakerst(int[] a) {
        for (int i = 0; i < a.length-1; i++) {
            if (a[i] > a[i+1]) {
                swap(a, i, i+1);
            }
        }
    } // flyttStørstBakerst

    /**
     * helper function that swaps (in place) two elements in an array (int[])
     * @param a int[] array
     * @param i index of first element to swap
     * @param j index of second element to swap
     */
    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    } // swap

    ///// Oppgave 2 //////////////////////////////////////

    /**
     *
     * @param a Array som skal sjekkes
     * @return Int av antall ulike verdier i arrayet
     */
    public static int antallUlikeSortert(int[] a) {
        // Sjekker om lengde er 1 eller 0. Begge er gyldige tilfeller.
        if (a.length < 2){ return a.length; }

        // Oppretter opptellingsvariabel som til slutt skal returneres
        // Satt til 1 fordi det er gitt at et array på 2+ verdier har minst 1 unik verdi
        int antall = 1;

        // Løper gjennom arrayet
        for (int i = 0; i < a.length - 1; i++){
            // Sjekker om en verdi er større enn neste i arrayet
            // Hvis så er ikke arrayet sortert i stigende rekkefølge
            if (a[i] > a[i+1]){
                // KAST FEILMELDING: Ikke sortert
                throw new IllegalStateException("Arrayet er ikke sortert i stigende rekkefølge.");
            }
            // Sjekker om verdi er ulik fra neste verdi i arrayet
            if (a[i] != a[i+1]){
                antall++;
            }
        } // Om koden kommer hit er arrayet gyldig og sortert
        return antall;
    }

    ///// Oppgave 3 //////////////////////////////////////

    /**
     * antallUlikeSortert() Denne metoden looper igjennom et usortert heltallsarray for å finne antall ulike verdier i arrayet.
     * @param a             Et usortert heltallsarray
     * @return              Metoden returnere antall ulike verdier i det usorterte heltallsarrayet a
     */
    public static int antallUlikeUsortert(int[] a) {
        // Lager en hjelpevariabel som holder tellingen på antall ulike tall i listen. Starter på 0
        int antallUlike = 0;

        // Gjør sjekker på lengden av arrayet for corner cases:
        if (a.length == 0) { // Sjekker om arrayet er tomt og returner 0 antall ulike dersom det er tomt.
            return antallUlike;
        } else if (a.length == 1) { // Sjekker om arrayet kun består av ett tall og dersom dette er sant returneres antallUlike = 1. Deretter
            antallUlike += 1;
            return antallUlike;
        } else if (a.length > 1) { // Sjekker om arrayet består av flere enn 1 tall og setter antallUlike = 1
            antallUlike += 1;
        }

        // Kjører en ytre og en indre loop:
        // Ytre loop: Går igjennom alle tallene i listen fra indeks 1 til og med siste tall
        for ( int i = 1; i < a.length; i++) {
            // Lager en hjelpevariabel for å sjekke om den har funnet noen like verdier underveis i gjennom-loopingen av
            // den indre loopen
            boolean funnetLike = false;
            for (int j = i - 1; j >= 0; j--) {
                if (a[i] == a[j]) {
                    funnetLike = true;
                    break;
                }
            }
            // Øker antallUlike med 1 dersom funnetLike er false
            if (!funnetLike) {
                antallUlike += 1;
            }
        }

        // Returner antall ulike
        return antallUlike;
    }


    ///// Oppgave 4 //////////////////////////////////////

    // Setter opp en implementasjon av quicksort algoritmen basert på kompendiet delkapittel 1.3 for å bruke til å
    // sortere partallene og oddetallene effektivt etter at de er flyttet til hver sin side i arrayet

    // parteringsfunksjon som, basert på en skilleverdi, sorterer verdiene som er høyere enn denne til høyre, og
    // verdiene som er lavere enn denne til venstre
    public static int parter0(int[] a, int v, int h, int skilleverdi) {
        while (true) {                                  // stopper når v > h
            while (v <= h && a[v] < skilleverdi) v++;   // h er stoppverdi for v
            while (v <= h && a[h] >= skilleverdi) h--;  // v er stoppverdi for h

            if (v < h) {
                swap(a,v++,h--);                 // bytter om a[v] og a[h]
            } else return v;                     // a[v] er nåden første som ikke er mindre enn skilleverdi
        }
    }

    // Funksjon for å utføre parteringen over, men på et slikt vis at skilleverdien flyttes helt bakerst først,
    // slik at man ikke trenger å flytte en haug med verdier når man skal plassere skilleverdien til slutt.
    public static int sParter0(int[] a, int v, int h, int indeks) {
        // Flytter skilleverdien helt til slutt i listen
        swap(a, indeks, h);
        // sorterer basert på skilleverdien og finner posisjonen som skilleverdien skal settes inn i igjen
        int pos = parter0(a, v, h - 1, a[h]);
        // Flytter skilleverdien til rett plass
        swap(a, pos, h);
        // Returner denne posisjonen slik at vi kan bruke rekursjon for å gjøre nye quicksorts på hver side av
        // denne posisjonen som vi nå vet at er på riktig plass
        return pos;
    }

    // Kjører quicksortalgoritmen rekursivt for å sortere listene på hver side av skilleverdiene som havner på rett plass,
    // men som etterlater seg lister til høyre og venstre som må sorteres.
    public static void quicksort(int[] a, int v, int h) { // en public metode
        if (v >= h) return;  // a[v:h] er tomt eller har maks ett element
        int k = sParter0(a, v, h, (v + h)/2);  // bruker midtverdien
        quicksort(a, v, k - 1);     // sorterer intervallet a[v:k-1]
        quicksort(a, k + 1, h);     // sorterer intervallet a[k+1:h]
    }

    /**
     * delsortering()   Denne metoden tar inn en liste og deler den inn i oddetall til venstre og partall til høyre, før
     *                  den sorterer hver sin side i stigende rekkefølge in-place med quicksort
     * @param a         En liste med tall
     */
    public static void delsortering(int[] a) {
        // Utfører sortering av oddetall og partall før disse igjen sorteres i stigende rekkefølge med quicksort

        // Sjekker antall oddetall for å vite hvor man skal sette første skilleverdi i quicksort algoritmene senere
        int antall_oddetall = 0;
        for (int k : a) {
            if (k % 2 != 0) {
                antall_oddetall += 1;
            }
        }

        // Begynner på flyttingen av oddetall til venstre og partall til høyre med partisjoneringsalgoritmen vist
        // i kompendiet under delkapittel 1.3 fra programkode 1.3.9 a), men som er endret for å dele inn oddetall
        // og partall, istedenfor "høyere enn" og "lavere enn"
        int l = 0;                                  // Indikerer start
        int r = a.length - 1;                       // Indikerer slutt
        while (l < r) {                             // Så lenge start har lavere indeks enn slutt så kjører algoritmen
            while (l <= r && a[l] % 2 != 0) l++;    // Øker starts indeks så lenge det står oddetall på riktig plass
            while (l <= r && a[r] % 2 == 0) r--;    // Reduserer slutts indeks så lenge det står partall på riktig plass

            // Dersom while løkkene over er kjørt igjennom betyr det at det er både ett partall og ett odetall
            // som står på feil plass, så da bytter man disse:
            if(l < r) {
                swap(a, l, r);
            }
        }

        // Sorterer de to sidene hver for seg med quicksort-algoritmen
        // Oddetallssiden
        quicksort(a, 0, antall_oddetall-1);     // Kjører quicksort
        // Partallssiden
        quicksort(a, antall_oddetall, a.length-1);     // Kjører quicksort
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {

        // 1. lag en temp verdi som er siste elementet i listen
        int n = a.length;       // mer effektivt å hente ut listen sin lengde kun 1 gang? gjør koden penere

        if (n == 0) return;     // returnerer hvis listen er tom, fordi da vil en rotert liste være helt lik.

        char temp = a[n - 1];   // lagrer verdien til siste verdien i listen

        // 2. start nest bakerst i listen og "flytt" (kopier) ett og ett element til høyre til du er kommet til indeks 0
        for (int i = n - 1; i > 0; i--) {   // n - 1 er siste indeks i listen
            a[i] = a[i-1];                  // flytter elementet til venstre til plass [i];
        }

        // 3. legg temp verdien i starten av listen.
        a[0] = temp;
    }

    ///// Oppgave 6 //////////////////////////////////////

    /**
     * roterer en liste in place k plasser, dersom k er negativ roterer den listen mot venstre
     * eksempel:
     *              a = {'a', 'b', 'c'} og k = 2    ---->   {'b', 'c', 'a'}
     * @param a en liste (char[])
     * @param k antall plasser som skal roteres, kan være negativ.
     */
    public static void rotasjon(char[] a, int k) {
        int n = a.length;

        // sjekk om listen er tom, har ett element
        if (n == 0 || n == 1) return;

        // 3. sjekk om k er større enn lengden på listen og endre k til riktig verdi så man får et tilsvarende resultat
        //    men slipper unødvendige iterasjoner.
        if (k > a.length || k < -a.length) {
            k = k % n;
        }

        // 4. sjekk om k er negativ og kall den respektive hjelpefunksjonen (roterH / roterV)
        if (k > 0) {
            roterH(a, k);
        } else {
            roterV(a, -k);
        }
    }

    private static void roterH(char[] a, int k) {
        int n = a.length;

        // 1. holde de k siste elementene som temp verdier
        char[] temp = new char[k];
        System.arraycopy(a, n - k, temp, 0, k);

        // 2. starte bakerst (indeks i) og flytte element i indeks (i-k) til indeks (i)
        for (int i = n - 1; i >= k; i--) {
            a[i] = a[i-k];  // kopierer elementet som ligger k plasser til venstre for elementet til indeks i
        }

        // 3. legge elementer i temp på indeks 0 til og med k-1
        System.arraycopy(temp, 0, a, 0, k);
    }

    private static void roterV(char[] a, int k) {
        int n = a.length;

        // 1. holde de k første elementene som temp verdier
        char[] temp = new char[k];
        System.arraycopy(a, 0, temp, 0, k);

        // 2. starte først (indeks i) og flytte element i indeks (i+k) til indeks (i)
        for (int i = 0; i < n - k; i++) {
            a[i] = a[i+k];
        }

        // 3. legge elementer i temp på indeks a.length - k (-1?) til og med a.length - 1
        System.arraycopy(temp, 0, a, n-k, k);
    }

    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {
        // Finner antall bokstaver i lengste streng
        // Bruker tidligere laget maks() metode
        int lengsteStreng = maks(new int[] {s.length(), t.length()});

        // Oppretter streng som skal manipuleres og returneres
        String resultat = "";

        // Løper gjennom t.o.m. lengste streng
        for (int i = 0; i < lengsteStreng; i++){
            // Streng s, dersom streng har flere bokstaver
            if (s.length() > i){ resultat += s.charAt(i); }

            // Streng t, dersom streng har flere bokstaver
            if (t.length() > i){  resultat += t.charAt(i); }
        }
        return resultat;
    }

    /// 7b)
    public static String flett(String... s) {
        // Oppretter streng som skal manipuleres og returneres
        String resultat = "";

        // Holder antall tegn lagt til i resultat-strengen pr. løkke-gjennomgang
        // Når 0 er ingen nye tegn lagt til og resultat ferdig
        int antLagtTil = 1;

        // Holder lengde på resultat-strengen, for sammenlikning etter ny løkke-gjennomgang.
        int lengdePre;

        // Index til while-løkken
        int index = 0;

        // Kjører så lenge nye tegn blir lagt til i resultat
        while (antLagtTil != 0) {
            // Finner lengde på resultat-streng før ny gjennomgang
            lengdePre = resultat.length();

            // Løkke som handler på alle strenger i s[]
            for (String tegnStreng : s) {
                // Sjekker om index eksisterer i strengen
                if (tegnStreng.length() > index){
                    resultat += tegnStreng.charAt(index);
                }
            }
            // regner ut endring i lengde på resultat-streng
            antLagtTil = resultat.length() - lengdePre;
            index++;
        }
        return resultat;
    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {
        // Sjekk for tilfelle lengde 0
        if (a.length == 0){
            return new int[] {};    // Returnerer nytt tomt array og ikke a fordi
        }                           // a returnert vil peke på samme plass i minne
        // Sjekk for tilfelle lengde 1
        if (a.length == 1){
            return new int[] {0};
        }

        // Array som blir fylt inn og returneres
        int[] indeks = new int[a.length];

        // Hjelpearray som sorteres
        int[] aSortert = new int[a.length];
        // Kopierer verdier fra a
        for (int i = 0; i < a.length; i++){
            aSortert[i] = a[i];
        }
        // Sorterer arrayet
        quicksort(aSortert, 0, aSortert.length-1);

        // Løper gjennom aSortert
        for (int i = 0; i < aSortert.length; i++){
            // Ser etter gjeldende sortert verdi i a
            for (int j = 0; j < a.length; j++){
                // Sjekker om match, og om at indeks ikke allerede er brukt
                if (a[j] == aSortert[i] && !verdiEksisterer(indeks, j)){
                    // Oppdaterer indeks[] med indeks til match
                    indeks[i] = j;
                    break;
                }
            }
        }
        return indeks;
    }

    /**
     * Ser om en gitt verdi eksisterer i et array
     * Hjelpemetode til oppgave 8. Separerer koden for bedre lesbarhet
     * @param a Array som skal letes i
     * @param verdi Vardi som skal letes etter
     * @return True om verdien eksisterer, False om den ikke eksisterer
     */
    public static boolean verdiEksisterer (int [] a, int verdi){
        boolean eksisterer = false;
        for (int aVerdi : a) {
            if (aVerdi == verdi) {
                eksisterer = true;
                break;
            }
        }
        return eksisterer;
    }

    ///// Oppgave 9 //////////////////////////////////////
    /**
     * Tar inn heltallsarray og returnerer indekser til de 3 minste verdiene
     * @param a heltallsarray
     * @return  heltallsarray med indekser til de tre minste verdiene [minst, nest minst, tredje minst].
     */
    public static int[] tredjeMin(int[] a) {

        // hvis feil input
        if (a.length < 3) throw new NoSuchElementException("a.length (" + a.length + ") er mindre enn 3!");

        int[] treStørste = new int[3]; // plass til 3 tall
        System.arraycopy(a, 0, treStørste, 0, 3); // kopierer over 3 første tallene i a

        treStørste = indekssortering(treStørste); // oppdaterer treStørste til å være indekssortert minst -> størst

        int i_m = treStørste[0];    // indeks minst
        int i_nm = treStørste[1];   // indeks nest-minst
        int i_tm = treStørste[2];   // indeks tredje-minst

        int m = a[i_m];     // minste verdi
        int nm = a[i_nm];   // nest minste verdi
        int tm = a[i_tm];   // tredje minste verdi


        for (int i = 3; i < a.length; i++) {    // i = 3 fordi indeks 0 tom. 2 allerede er sjekket.
            int value = a[i];
            if (value < tm) {           // hvis value er minstre enn tredje minste verdi

                if (value < nm) {       // hvis value er mindre enn nest minst verdi

                    tm = nm;    // oppdaterer tredje minste sin verdi
                    i_tm = i_nm;// oppdaterer tredje minste sin indeks

                    if (value < m) {    // hvis value er mindre enn minste verdi

                        nm = m;     // oppdaterer nest minste sin verdi
                        i_nm = i_m; // oppdaterer nest minste sin indeks

                        m = value;  // oppdaterer minste sin verdi
                        i_m = i;    // oppdaterer minste sin indeks

                    } else {            // ikke mindre enn minst

                        nm = value; // oppdaterer nest minste verdi
                        i_nm = i;   // oppdaterer nest minste sin indeks
                    } // if (value < m)
                } else {            // ikke mindre enn nest minst

                    tm = value; // oppdaterer tredje minste verdi
                    i_tm = i;   // oppdaterer tredje minste sin indeks

                } // if (value < nm)
            } // if (value < tm)
        } // for
        return new int[] {i_m, i_nm, i_tm};
    }

    ///// Oppgave 10 //////////////////////////////////////

    /**
     * gjør om en char til integer-verdi, implementert fordi den var i oppgaven, men ikke brukt..
     * @param bokstav char
     * @return integer-verdien til char(bokstav)
     */
    public static int bokstavNr(char bokstav) {
        int nr = bokstav;
        return nr;
    }

    /**
     * Sjekker om String a finnes i String b.
     * @param a String
     * @param b String
     * @return True/False avhengig av om a finnes i b eller ikke.
     */
    public static boolean inneholdt(String a, String b) {
        int[] arr = new int[256];               // Array som inneholder indekser til alle chars i ASCII tabell
        for (char letter : b.toCharArray()) {   // For each character in string b
            arr[letter] += 1;                   // Inkrementerer verdien til bokstaven letter
        }

        for (char letter : a.toCharArray()) {
            if (arr[letter] >= 1) {             // Sjekker om det er "fler bokstaver igjen" i bokstavindeks-listen
                arr[letter]--;                  // "Bruker" opp bokstaven fra listen med bokstavindekser og antall
            } else {
                return false;                   // Returnerer false dersom bokstaven ikke lenger finnes i bokstavindeks-listen
            }
        }

        return true; // a eksisterer i b
    } // inneholdt

}  // Oblig1
