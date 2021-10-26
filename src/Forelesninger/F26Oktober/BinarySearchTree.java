package Forelesninger.F26Oktober;

public class BinarySearchTree {

    public static void main(String[] args) {
        BST tree = new BST();
        int[] values = {9, 7, 1, 3, 5, 11, 13, 10, 8};

        for (int i = 0; i < values.length; i++) {
            tree.insert(values[i]);
            tree.print();
            System.out.println("");
        }


        System.out.println(tree);
    }

    /**
     * Node-klasse som har alt vi trenger for hver eneste node i
     * det binære søke-treet
     */
    public static class Node {
        Node parent;
        Node leftChild;
        Node rightChild;
        int value;

        /**
         * Lager en node med null som foreldre og barnepekere
         * @param value
         */
        Node(int value) {
            this.value = value;
        }

        /**
         * Lager en node med forelder, men null som barnepekere
         * @param value
         * @param parent
         */
        Node(int value, Node parent) {
            this(value);
            this.parent = parent;
        }

        Node(int value, Node parent, Node leftChild, Node rightChild) {
            this(value, parent);
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        void printPreOrderRecursive() {
            System.out.print(this.value + ", ");
            if (leftChild != null) {
                leftChild.printPreOrderRecursive();
            }
            if (rightChild != null) {
                rightChild.printPreOrderRecursive();
            }
        }
    }

    /**
     * Binary Search Tree som har rot-noden og annen info om vårt søketre.
     * Har også metoden for innleggning, fjerning, søking, etc
     */
    public static class BST {
        Node root;

        /**
         * Denne funksjonen søker etter verdien i søketreet,
         * men returnerer null om verdien ikke finnes.
         * @param value
         * @return
         */
        Node search(int value) {
            Node p = root;
            while (p != null || p.value == value) {
                // verdien er større enn p
                if (value > p.value) {
                    p = p.rightChild;
                } else if (value < p.value) {
                    p = p.leftChild;
                } else {
                    break;
                }
            }
            return p;
        }

        void insert(int value) {

            // Treet vårt er tomt, vi lager første node!
            if (root == null) {
                root = new Node(value);
                return;
            } else {    // treet er ikke tomt, vi må legge verdi på rett plass
                Node p = root;
                Node q = null;  // parent til p
                while (p != null) {
                    q = p;
                    if (value >= p.value) {
                        p = p.rightChild;
                    } else {
                        p = p.leftChild;
                    }
                }

                p = new Node(value, q);

                if (value >= q.value) {
                    q.rightChild = p;
                } else {
                    q.leftChild = p;
                }
            }
        }

        public void print() {
            root.printPreOrderRecursive();
        }
    }
}
