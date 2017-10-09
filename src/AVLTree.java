public class AVLTree {
    private Node root;

    /* Returns true if value is not already in the tree.
       Otherwise, return false. */
    public boolean add(int value) {
        Node aux = add(root,value);
        if(aux != null){
            root = aux;
            return true;
        }
        return false;
    }
    /*public boolean remove(int value) {
        if(root.left == null && root.right==null) {
            if (root.value == value) {
                root = null;
                return true;
            }
            return false;
        }
        Boolean flag = Boolean.FALSE;
        root = remove(root,value,flag);
        return flag.booleanValue();
    }

    private Node remove(Node n,int value, Boolean flag){
        if(n.value == value){
            flag = Boolean.TRUE;
            if(n.right!=null) {
                Node aux = minimum(n.right);
                aux.setLeft(n.left);
                if(aux.value != n.right.value)
                    aux.setRight(n.right);
                return balance(aux,0);
            }
            else if(n.left!=null) {
                Node aux = maximum(n.left);
                aux.setRight(n.right);
                if(aux.value != n.left.value)
                    aux.setLeft(n.left);
                return balance(aux,0);
            }
            return null;
        }
        else if(n.value < value && n.right !=null) {
            n.setRight(remove(n.right, value, flag));
        }
        else if(n.value > value && n.left!= null){
            n.setLeft(remove(n.left,value, flag));
        }
        if(flag.booleanValue()){
            return balance(n,0);
        }
        return n;
    }

    private Node minimum(Node n){
        if(n.left != null){
            Node aux = n.left;
            if(aux.left!=null)
                return minimum(aux);
            else {
                n.setLeft(aux.right);
                return aux;
            }
        }
        else
            return n;
    }

    private Node maximum(Node n){

        if(n.right!=null){
            Node aux = n.right;
            if(aux.right!=null)
                return maximum(aux);
            else {
                n.setRight(aux.left);
                return aux;
            }
        }
        else
            return n;
    }
   */
    public int countR(Node n){
        if(n ==null)
            return 0;
        int leftn = 0;
        int rightn = 0;
        if(n.left!=null)
            leftn = countR(n.left);
        if(n.right!=null)
            rightn = countR(n.right);
        return 1 + leftn + rightn;
    }
    public boolean remove(int value) {
        if(root.left == null && root.right==null) {
            if (root.value == value) {
                root = null;
                return true;
            }
            return false;
        }
        int dim = countR(root);
        root = remove(root,value);
        return(dim == countR(root))?false:true;
    }

    private Node remove(Node n,int value){
        if(n.value == value){
            if(n.right!=null) {
                Node aux = minimum(n.right);
                aux.setLeft(n.left);
                if(aux.value != n.right.value)
                    aux.setRight(n.right);
                return aux;
            }
            else if(n.left!=null) {
                Node aux = maximum(n.left);
                aux.setRight(n.right);
                if(aux.value != n.left.value)
                    aux.setLeft(n.left);
                return aux;
            }
            return null;
        }
        else if(n.value < value && n.right !=null) {
            n.setRight(remove(n.right, value));
            return n;
        }
        else if(n.value > value && n.left!= null){
            n.setLeft(remove(n.left,value));
            return n;
        }
        else
            return n;
    }

    private Node minimum(Node n){
        if(n.left != null){
            Node aux = n.left;
            if(aux.left!=null)
                return minimum(aux);
            else {
                n.setLeft(aux.right);
                return aux;
            }
        }
        else
            return n;
    }

    private Node maximum(Node n){

        if(n.right!=null){
            Node aux = n.right;
            if(aux.right!=null)
                return maximum(aux);
            else {
                n.setRight(aux.left);
                return aux;
            }
        }
        else
            return n;
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

    private Node balance(Node current){
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
