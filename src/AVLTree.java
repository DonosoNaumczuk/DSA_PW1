import Test.TreePrinter;

public class AVLTree implements java.io.Serializable {
    private Node root;
    private int nodeQty;

    public AVLTree() {
        root = null;
        nodeQty = 0;
    }

    /* Returns true if value is not already in the tree.
       Otherwise, return false. */
    public boolean add(int value) {
        Node aux = add(root,value);
        if(aux != null){
            root = aux;
            nodeQty++;
            return true;
        }
        return false;
    }
    

    public int nodeQuantity() {
        return nodeQty;
    }

    public boolean remove(int value) {
        if(root == null)
            return false;

        if(nodeQty == 1) {
            if (root.value == value) {
                root = null;
                nodeQty = 0;
                return true;
            }
            else {
                return false;
            }
        }

        int oldQty = nodeQty;
        root = remove(root,value);
        return oldQty != nodeQuantity();
    }

    private Node remove(Node current,int value) {
        if(current.value == value){
            nodeQty--;
            if(current.right!=null) {
                Node aux = minimum(current.right);
                aux.setLeft(current.left);
                if(aux.value != current.right.value)
                    aux.setRight(current.right);
                return aux;
            }
            else if(current.left!=null) {
                Node aux = maximum(current.left);
                aux.setRight(current.right);
                if(aux.value != current.left.value)
                    aux.setLeft(current.left);
                return aux;
            }
            return null;
        }
        else if(current.value < value && current.right !=null) {
            current.setRight(remove(current.right, value));
            return current;
        }
        else if(current.value > value && current.left!= null){
            current.setLeft(remove(current.left,value));
            return current;
        }
        else
            return current;
    }

    private Node minimum(Node root) {
        if(root.left != null){
            Node aux = root.left;
            if(aux.left!=null)
                return minimum(aux);
            else {
                root.setLeft(aux.right);
                return aux;
            }
        }
        else
            return root;
    }

    private Node maximum(Node root) {
        if(root.right!=null){
            Node aux = root.right;
            if(aux.right!=null)
                return maximum(aux);
            else {
                root.setRight(aux.left);
                return aux;
            }
        }
        else
            return root;
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
        return balance(current);
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

    private Node balance(Node current) {
        int bf = current.getBalanceFactor();

        /* Left-Left case */
        if (bf > 1 && current.left != null && current.left.getBalanceFactor() >= 0)
            return rotateRight(current);

        /* Right-Right case */
        if (bf < -1 && current.right != null && current.right.getBalanceFactor() < 0)
            return rotateLeft(current);

        /* Left-Right case */
        if (bf > 1 && current.left != null && current.left.getBalanceFactor() <= 0) {
            return rotateLeftRight(current);
        }

        /* Right-Left case */
        if (bf < -1 && current.right != null && current.right.getBalanceFactor() > 0) {
            return rotateRightLeft(current);
        }

        return current;
    }

    private static class Node implements TreePrinter.PrintableNode {
        int value;
        int height;
        Node left;
        Node right;

        private Node(int value) {
            this.value = value;
            height = 1;
        }

        public Node getLeft(){
            return left;
        }

        public Node getRight(){
            return right;
        }

        public String getText(){
            return String.valueOf(value);
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
            height = ((heightl>heightr)?heightl:heightr) + 1;
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

    private void print(Node current, String prev, boolean last) {
        if(current!=null) {
            if(last) {
                prev=prev.concat("\\- ");
            }
            else {
                prev=prev.concat("|- ");
            }
            System.out.println(prev+current.value);
        }
    }
    
    private void printTree(Node current, String prev){
        boolean right=current.right==null;
        boolean left=current.left==null;
        if(!right){
            if(!left) {
                print(current.right, prev, false);          //puede cambiar
                printTree(current.right, prev+"|  ");
                print(current.left, prev, true);
                printTree(current.left,prev+"   ");
            }else{
                print(current.right, prev, true);
                printTree(current.right,prev+"   ");
            }
        }else{
            if(!left){
                print(current.left, prev, true);
                printTree(current.left,prev+"   ");
            }
        }
    }
}
