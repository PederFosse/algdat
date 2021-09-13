package hjelpeklasser;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TabellTest {

    @Test
    void randPerm() {
        int[] tilfeldigtabell = Tabell.randPerm(5);

        int lengde = 5;

        assertEquals(lengde, tilfeldigtabell.length);

        int[] tabellkopi = Arrays.copyOf(tilfeldigtabell, tilfeldigtabell.length);

        for (int i : tabellkopi) {
            int counter = 0;
            int expected = 1;
            for (int k : tilfeldigtabell) {
                if (k == i) counter++;
            }
            assertEquals(expected, counter);
        }
    }

    @Test
    void maks() {
        int[] values = {3, 6, 1, 5, 4};
        int maks = Tabell.maks(values);
        int truth = 1;

        assertEquals(truth, maks);
    }

    @Test
    void min() {
        int[] values = {3, 6, 1, 5, 4};
        int min = Tabell.min(values);
        int truth = 2;

        assertEquals(truth, min);
    }

    @Test
    void nestMaks() {
        int[] values = {3, 6, 1, 5, 4};
        int[] nestMaks = Tabell.nestMaks(values);

        int[] truth = new int[] {1, 3};

        assertArrayEquals(truth, nestMaks);
    }

    @Test
    void nestMaks2() {
        int[] values = {3, 6, 1, 5, 4};
        int[] nestMaks = Tabell.nestMaks2(values);

        int[] truth = new int[] {6, 5};

        assertArrayEquals(truth, nestMaks);
    }
}