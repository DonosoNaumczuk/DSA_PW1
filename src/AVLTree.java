public class AVLTree {
    private Node root;

    /* Returns true if value is not already in the tree.
       Otherwise, return false. */
    public boolean add(int value) {
        if(root == null) {
            root = new Node(value);
            return true;
        }
        return add(root, value) != null;
    }

    public boolean remove(int value) { //Se podra mejorar?
        Boolean flag = false;
        root = remove(root,value,flag);
        return flag;
    }

    private Node remove(Node current, int value, Boolean flag){
        if(current == null){
            return current;
        }
        if(current.value > value){
            current.setLeft(remove(current,value,flag));
        } else if(current.value < value){
            current.setRight(remove(current,value,flag));
        } else {
            flag = true;
            if (current.left == null){
                return current.right;
            } else if(current.right == null){
                return current.left;
            } else {
                Node aux = minimun(current.right);
                current.value = aux.value;
            }
        }
        //intento de balaceo :)
        if (flag){
            rotate(current,0);
        }
        return current;
    }
    //retorna el minimo del arbol y lo saca del mismo
    private Node minimun(Node root){
        Node aux = root.left;
        if(root.left.left == null){
            root.setLeft(remove(root.left,root.left.value,true));
        } else {
            minimun(root.left);
        }
        return aux;
    }

    private Node add(Node current, int value) {
        if(current == null)
            return new Node(value);
        if(value == current.value)
            return null;

        Node aux;
        if(value > current.value) {
            aux = add(current.right, value);
            if(aux == null)
                return null;
            current.setRight(aux);
        }
        else {
            aux = add(current.left, value);
            if(aux == null)
                return null;
            current.setLeft(aux);
        }
        return rotate(current, value);
    }

    /* Right-Right case */
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.setRight(newRoot.left);
        newRoot.setLeft(root);
        return newRoot;
    }

    /* Left-Left case */
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.setLeft(newRoot.right);
        newRoot.setRight(root);
        return newRoot;
    }

    /* Left-Right case */
    private Node rotateLeftRight(Node root) {
        root.setLeft(rotateLeft(root.left));
        return rotateRight(root);
    }

    /* Right-Left case */
    private Node rotateRightLeft(Node root) {
        root.setRight(rotateRight(root.right));
        return rotateLeft(root);
    }

    private Node rotate(Node current, int value){
        int bf = current.getBalanceFactor();

        /* Left-Left case */
        if (bf > 1 && current.left.value >= value)
            return rotateRight(current);

        /* Right-Right case */
        if (bf < -1 && current.right.value <= value)
            return rotateLeft(current);

        /* Left-Right case */
        if (bf > 1 && current.left.value < value) {
            return rotateLeftRight(current);
        }

        /* Right-Left case */
        if (bf < -1 && current.right.value > value) {
            return rotateRightLeft(current);
        }

        return current;
    }

    private static class Node {
        int value;
        int height;
        Node left;
        Node right;

        private Node(int value) {
            this.value = value;
            height = 1;
        }

        private void setLeft(Node left){
            this.left = left;
            computeHeight();
        }

        private void setRight(Node right){
            this.right = right;
            computeHeight();
        }

        /* Computes height and update it */
        private void computeHeight() {
            int heightl = (left==null)?0:left.height;
            int heightr = (right==null)?0:right.height;
            height = (heightl>heightr)?heightl:heightr + 1;
        }

        private int getBalanceFactor(){
            int heightl = (left==null)?0:left.height;
            int heightr = (right==null)?0:right.height;
            return heightl-heightr;
        }
    }

    /*----------------------------------------------------------------
    ---------------------------- PRINT -------------------------------
    ----------------------------------------------------------------*/

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
