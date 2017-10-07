public class AVLTree {
    private Node root;

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public boolean add(int number) {
        Node aux = add(root, number);
        if(aux != null) {
            root = aux;
            return true;
        }
        return false;

    }

    /* Right-Right case */
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        return newRoot;
    }

    /* Left-Left case */
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        return newRoot;
    }

    /* Left-Right case */
    private Node rotateLeftRight(Node root) {
        root.left = rotateLeft(root.left);
        return rotateRight(root);
    }

    private Node rotateRightLeft(Node root) {
        root.right = rotateRight(root.right);
        return rotateLeft(root);
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


    //nada de abajo esta testeado
     public void print() {
        if(root!=null) {
            System.out.println("\\- " + root.value);
            printTree(root,"   ");
        }
        //se puede agregar algo para cuando es null
    }

    private void print(Node n, String prev, boolean last) {
        if(n!=null) {
            if(last) {
                prev=prev.concat("\\- ");
            }
            else {
                prev=prev.concat("|- ");
            }
            System.out.println(prev+n.value);
        }
    }
    
    private void printTree(Node n, String prev){
        boolean right=n.right==null;
        boolean left=n.left==null;
        if(!right){
            if(!left) {
                print(n.right, prev, false);          //puede cambiar
                printTree(n.right, prev+"|  ");
                print(n.left, prev, true);
                printTree(n.left,prev+"   ");
            }else{
                print(n.right, prev, true);
                printTree(n.right,prev+"   ");
            }
        }else{
            if(!left){
                print(n.left, prev, true);
                printTree(n.left,prev+"   ");
            }
        }
    }
}
