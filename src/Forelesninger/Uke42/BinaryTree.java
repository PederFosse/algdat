package Forelesninger.Uke42;

import java.util.ArrayDeque;

public class BinaryTree {
    static class BinaryTreeNode {
        char value;
        BinaryTreeNode leftChild;
        BinaryTreeNode rightChild;

        BinaryTreeNode(char value) {
            this.value = value;
        }

        BinaryTreeNode addLeftChild(char value) {
            this.leftChild = new BinaryTreeNode(value);
            return this.leftChild;
        }

        BinaryTreeNode addRightChild(char value) {
            this.rightChild = new BinaryTreeNode(value);
            return this.rightChild;
        }
    }

    static void printLevelOrder(BinaryTreeNode root) {
        ArrayDeque<BinaryTreeNode> queue = new ArrayDeque<>();

        // legg til rot-noden
        queue.addFirst(root);

        while(!queue.isEmpty()) {
            // ta ut første fra køen
            BinaryTreeNode current = queue.removeFirst();

            // legg til current sine 2 barn i køen
            if (current.leftChild != null) {
                queue.addLast(current.leftChild);
            }
            if (current.rightChild != null) {
                queue.addLast(current.rightChild);
            }

            // skriv ut
            System.out.print(current.value + " ");
        }
    }

    static void printPreOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }

        System.out.print(node.value + " ");
        printPreOrder(node.leftChild);
        printPreOrder(node.rightChild);
    }

    static void printInOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }

        printInOrder(node.leftChild);
        System.out.print(node.value + " ");
        printInOrder(node.rightChild);
    }

    static void printPostOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }

        printPostOrder(node.leftChild);
        printPostOrder(node.rightChild);
        System.out.print(node.value + " ");
    }

    static void printPreOrderNonRecursive(BinaryTreeNode root) {
        ArrayDeque<BinaryTreeNode> stack = new ArrayDeque<>();

        stack.addLast(root);

        while(!stack.isEmpty()) {
            BinaryTreeNode current = stack.removeLast();
            if (current.rightChild != null) {
                stack.addLast(current.rightChild);
            }
            if (current.leftChild != null) {
                stack.addLast(current.leftChild);
            }

            System.out.print(current.value + " ");
        }
    }

    public static void main(String[] args) {
        // lager rot-noden
        BinaryTreeNode root = new BinaryTreeNode('A');

        // legg inn resterende noder på nivå 1
        BinaryTreeNode b = root.addLeftChild('B');
        BinaryTreeNode c = root.addRightChild('C');

        // legg inn alle noder på nivå 2
        BinaryTreeNode d = b.addLeftChild('D');
        BinaryTreeNode e = b.addRightChild('E');

        BinaryTreeNode f = c.addLeftChild('F');
        BinaryTreeNode g = c.addRightChild('G');

        printLevelOrder(root);
        System.out.println();

        printPreOrder(root);
        System.out.println();

        printInOrder(root);
        System.out.println();

        printPostOrder(root);
        System.out.println();

        printPreOrderNonRecursive(root);
    }
}
