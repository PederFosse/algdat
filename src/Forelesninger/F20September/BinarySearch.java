package Forelesninger.F20September;

public class BinarySearch {
    public static void main(String[] args) {
        int[] values = {1, 2, 4, 8, 17, 19, 22};
        System.out.println(binarySearch(22, values, 0, values.length - 1));
    }

    static int binarySearch(int searchValue, int[] values, int left, int right) {
        int middle = (left + right) / 2;


        if (right - left == 0) {
            if (values[middle]== searchValue) {
                return middle;
            } else {
                return -left;
            }

        }

        if (searchValue >= values[middle]) {
            if (values[middle]== searchValue) {
                return middle;
            }
            return binarySearch(searchValue, values, middle + 1, right);
        } else {
            return binarySearch(searchValue, values, left, middle - 1);
        }
    }
}
