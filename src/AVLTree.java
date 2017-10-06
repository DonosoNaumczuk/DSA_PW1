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
            System.out.println("\\-" + root.toString());
            printTree(root,0);
        }
        //se puede agregar algo para cuando es null
    }

    private void print(Node n, int depth, boolean last) {
        if(n!=null) {
            String aux=" ";
            for (int i=1;i<depth;i++){
                aux=aux.concat(" ");
            }
            if(last) {
                aux=aux.concat("\\");
            }
            else {
                aux=aux.concat("|");
            }
            System.out.println(aux+n.value);
        }
    }
    
    private void printTree(Node n, int depth){
        boolean right=n.right==null;
        boolean left=n.left==null;
        if(!right){
            if(!left) {
                print(n.right, depth+1, false);    //no me convence
                printTree(n.right,depth+1);        //lo que hice
                print(n.left, depth+1, true);
                printTree(n.left,depth+1);
            }else{
                print(n.right, depth+1, true);
                printTree(n.right,depth+1);
            }
        }else{
            if(!left){
                print(n.left, depth+1, true);
                printTree(n.left,depth+1);
            }
        }
    }
}
