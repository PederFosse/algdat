package Forelesninger;

public class CircularBuffer {
    private char[] buffer;
    private int size;
    private int count = 0; // antall elementer som er i køen
    private int head = 0; // Peker til starten av køen
    private int tail = 0; // Peker til slutten av køen

    CircularBuffer(int size) {
        this.buffer = new char[size];
        this.size = size;
    }

    void pushBack(char value) {
        if (count + 1 > size) {
            throw new IndexOutOfBoundsException("Kan ikke legge til flere elementer");
        }
        buffer[tail] = value;
        tail = (tail + 1) % size;
        count++;
    }

    char popFront() {
        if (count == 0) {
            throw new IllegalArgumentException("Kan ikke fjerne fler elementer");
        }
        char retval = buffer[head];
        head = (head + 1) % size;
        count--;
        return retval;
    }

    int getCount() {
        return count;
    }

    public static void main(String[] args) {
        CircularBuffer buffer = new CircularBuffer(6);

        char[] values = "HFGSDFVAERO3289F2".toCharArray();

        for (int i = 0; i < values.length; i = i + 6) {
            // legg inn 3 elementer i buffer
            for (int j = 0; j < 6; j++) {
                if (values.length > i + j) {
                    buffer.pushBack(values[i+j]);
                }
            }

            // ta ut alt fra bufferet
            while(buffer.getCount() > 0) {
                System.out.println(buffer.popFront());
            }
            System.out.println();
        }
    }
}
