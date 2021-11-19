package Forelesninger;

import java.util.ArrayList;
import java.util.LinkedList;

public class Hashmap {
    public static void main(String[] args) {
        System.out.println("Hashmap test");

        int hashmapSize = 7;
        ArrayList<LinkedList<String>> hashmap = new ArrayList<>(hashmapSize);

        for (int i = 0; i < hashmapSize; i++) {
            hashmap.add(i, new LinkedList<>());
        }

        String[] strings = new String[10];
        strings[0] = "Peder";
        strings[1] = "Rikard";
        strings[2] = "Fredrik";
        strings[3] = "Skolevei";
        strings[4] = "Kanonkule";
        strings[5] = "Klinkekule";
        strings[6] = "Suppe";
        strings[7] = "Hangar";
        strings[8] = "Gjeter";
        strings[9] = "Sau";

        for (int i = 0; i < strings.length; i++) {
            int hash = hash(strings[i]);
            int hashmapIndex = computeHashmapIndex(hash, hashmapSize);
            System.out.println("legger inn " + strings[i] + " med hash: " + hash + " på plass " + hashmapIndex);
            hashmap.get(hashmapIndex).addFirst(strings[i]);
        }

        int hash = hash("skolevei");
        int hashmapIndex = computeHashmapIndex(hash, hashmapSize);
        System.out.println(hashmap.get(hashmapIndex).toString());

    }

    static int computeHashmapIndex(int hash, int size) {
        return hash % size;
    }

    static int hash(String data) {
        int hash = 0;
        for (int i = data.length() - 1; i >= 0; i--) {
            char c = data.charAt(i);
            hash = (hash + c) * 31;
        }

        if (hash < 0) hash = -hash;
        return hash;
    }
}
