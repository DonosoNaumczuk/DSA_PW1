/**
 * Created by brothers on 26/09/2017.
 */
public class AVLTree {
    private Node root;

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value)
        {
            this.value = value;
        }
    }

    public boolean add(int number)
    {
        Node aux = add(root, number);
        if(aux != null) {
            root = aux;
            return true;
        }
        return false;

    }
    //Faltarian hacer los rotate
    private Node add(Node current, int number) {
        if(current == null)
            return new Node(number);
        if(number == current.value)
            return null;

        Node aux;
        if(number > current.value) {
            aux = add(current.right, number);
            if(aux == null)
                return null;
            current.right = aux;
            return current;
        }

        aux = add(current.left, number);
        if(aux == null)
            return null;
        current.left = aux;
        return current;
    }

}
