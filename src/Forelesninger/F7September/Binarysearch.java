package Forelesninger.F7September;
import static hjelpeklasser.Tabell.*;

public class Binarysearch {

    public static void main(String[] args) {
        int[] values = {3, 5, 10, 11, 19, 20, 21, 33};
        skrivln(values);
        int target = 10;
        // int index = binarySearchRecursive(values, target, 0, values.length - 1);
        int index = binarySearch(values, target);
        System.out.println("Tallet: " + target + " ligger på indeks: " + index);
    }

    /**
     * returnerer index på target i array ved bruk av binærsøk
     * @param values int[] liste
     * @param target int som vi leter etter
     * @return index til target
     */
    public static int binarySearch(int[] values, int target) {
        // søk i intervallet [l:r]
        int l = 0;
        int r = values.length - 1;
        int m;

        // hvis midten er større
        while(l <= r) {
            m = (l + r) / 2;
            if (values[m] > target) {
                r = m-1;
            } else if (values[m] < target) {
                l = m+1;
            } else if (values[m] == target) {
                return m;
            } else {
                System.out.println("something went wrong..");
            }
        }
        return -1;
    }

    public static int binarySearchRecursive(int[] values, int target, int l, int r) {
        int m = (l+r) / 2;
        if (l > r) return -1;
        if (values[m] > target) {
            r = m - 1;
            return binarySearchRecursive(values, target, l, r);
        } else if (values[m] < target) {
            l = m+1;
            return binarySearchRecursive(values, target, l, r);
        } else if (values[m] == target) {
            return l;
        }
        return -1;
    }
}
