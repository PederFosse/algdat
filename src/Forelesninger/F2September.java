package Forelesninger;

public class F2September {

}

class TournamentTree {
    public static void main(String[] args) {
        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');

        Node semi_1 = Node.playMatch(a, b);
        Node semi_2 = Node.playMatch(d, c);
        Node winner = Node.playMatch(semi_1, semi_2);
        winner.print();
    }

    /**
     * A node class in a tournament tree
     * each node has a "pointer" to its children and a character value
     */
    public static class Node {
        Node leftChild;
        Node rightChild;
        Character value;

        Node(char value) {
            this.value = value;
        }

        static Node playMatch(Node a, Node b) {
            char winner = a.value > b.value ? a.value : b.value;

            Node parent = new Node(winner);
            parent.leftChild = a;
            parent.rightChild = b;

            return parent;
        }


        void print() {
            System.out.print(this.value + ", ");
            if (this.leftChild != null) {
                this.leftChild.print();
            }
            if (this.rightChild != null) {
                this.rightChild.print();
            }
        }
    }
}