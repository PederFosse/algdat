package Forelesninger;

import hjelpeklasser.Tabell;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class F31AugustTest {

    // option + enter og "add junit 5.4 to classpath"
    @org.junit.jupiter.api.Test
    void myFirstSort() {
        int values[] = {3, 6, 1, 5, 4};
        F31August.myFirstSort(values);

        int[] truth = {1, 3, 4, 5, 6};

        assertArrayEquals(truth, values);
    }

    @Test
    void findTwoMaxIndices() {
        int[] values = {4, 10, 2, 3, 11, 17, 12, 5, 6};
        int[] maxIndices = F31August.findTwoMaxIndices(values, 0, values.length);
        int[] truth = {6, 5};

        assertArrayEquals(truth, maxIndices);
    }
}