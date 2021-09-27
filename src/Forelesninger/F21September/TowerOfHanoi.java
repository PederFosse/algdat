package Forelesninger.F21September;

public class TowerOfHanoi {
    public static void main(String[] args) {
        towerOfHanoi(4);
    }

    /**
     * Lager en funksjon som kjører "tower of hanoi"
     * ved hjelp av rekursive kall
     * @param numPieces antall brikker å bruke i spillet
     */
    private static void towerOfHanoi(int numPieces) {
        hanoiMove(numPieces, 'A', 'C', 'B');
    }

    /**
     * Utfører et flytt i "tower of hanoi" (rekursivt)
     * @param pieceNumber Brikken vi skal flytte på nå
     * @param from stikken vi flytter fra => "A"
     * @param to stikken vi flytter til => "C"
     * @param helper hjelpestikken => "B"
     */
    private static void hanoiMove(int pieceNumber, char from, char to, char helper) {
        if (pieceNumber == 0) {
            return;
        }
        // 1.   Flytt alt fra stikke C til stikke B
        //      bruk stikke A som hjelpestikke
        hanoiMove(pieceNumber - 1, from, helper, to);

        // 2.   Flytt øverste brikke fra A til C
        //      Trenger ingen hjelpestikke, flytter bare 1 brikke
        System.out.println("FLytter " + (pieceNumber - 1) + " fra " + from + ", til " + to + ".");

        // 3.   Flytt alt på stikke B (fra punkt 1) til stikke C
        //      bruk stikke A som hjelpestikke
        hanoiMove(pieceNumber - 1, helper, to, from);
    }
}
