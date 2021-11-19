package egenTesting;

public class eksamensOving {

}

class DoubleLinkedList {

    public class Node {
        Node next;
        Node prev;
        char value;
    }

    Node head;
    Node tail;

    int size;

    void addLast(char value) {}
    void addFirst(char value) {}
    char removeLast() {return 'a';}
    char removeFirst() {return 'a';}
    void print() {}

    void remove(int index) {
        if (index == 0) {
            removeFirst();
        }
        else if (index == size-1) {
            removeLast();
        } else {
            Node current = null;

            if (index < size / 2) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.next;
                }
            }

            // q er current sin forrige, p er current sin neste
            Node q = current.prev, p = current.next;

            // "Omgår" current
            q.next = p;
            p.prev = q;

            // oppdater size
            size--;
        }
    }
    void remove(char value) {
        for (Node p = head; ;p = p.next) {
            if (p.value == value) {
                Node q = p.prev, r = p.next;

                q.next = r;
                r.prev = q;

                // oppdater size
                size--;
                return;
            }
        }

    }
}
