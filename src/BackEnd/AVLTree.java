package BackEnd;

import FrontEnd.TreePrinter;


public class AVLTree implements java.io.Serializable {
    private Node root;
    private int nodeQty;

    public AVLTree() {
        root = null;
        nodeQty = 0;
    }
    /**
     *
     * @param obj Object to wich we want to compare the tree
     * @return Returns false if the tree are different
     *         Otherwise, returns true
     */
    public boolean equals(Object obj){
        if(obj == null || !(obj.getClass().equals(getClass())))
            return false;
        AVLTree t = (AVLTree)obj;
        return compareTree(root,t.root);
    }

    /**
     *
     * @param n1 Node corresponding to the tree of the class.
     * @param n2 Node corresponding to the tree to wich we are
     *           comparing the tree of the class.
     * @return Returns false if subtrees of the given nodes are diferent
     *         Otherwise, returns true
     */
    private boolean compareTree(Node n1,Node n2){
        if((n1 == null && n2!= null) || (n2 == null && n1!= null) || n1.value != n2.value)
            return false;
        if(n1.getLeft()==null && n2.getLeft()==null && n1.getRight()==null && n2.getRight()==null)
            return n1.value == n2.value;
        if(n1.getLeft()==null && n2.getLeft()!=null || (n1.getLeft()!=null && n2.getLeft()==null))
            return false;
        if(n1.getRight()==null && n2.getRight()!=null || (n1.getRight()!=null && n2.getRight()==null))
            return false;
        boolean leftvalue =true;
        boolean rightvalue = true;
        if(n1.getLeft()!=null && n2.getLeft()!=null)
                leftvalue = compareTree(n1.getLeft(),n2.getLeft());
        if(n1.getRight()!=null && n2.getRight()!=null)
            rightvalue = compareTree(n1.getRight(),n2.getRight());
        return (leftvalue && rightvalue);
    }

    /**
     * 
     *  @param value Is the integer value wanted to add to the tree.
     *  @return Returns true if value is not already in the tree.
     *          Otherwise, return false.
     */
    public boolean add(int value) {
        Node aux = add(root,value);
        if(aux != null){
            root = aux;
            nodeQty++;
            return true;
        }
        return false;
    }
    /**
     * @return Returns the quantity of nodes in the tree.
     */
        public int getNodeQty(){
            return nodeQty;
        }

    /**
     * 
     * @param root Root node of the tree where we want to add the value.
     * @param value Integer value to add to the tree.
     * @return Returns null if the value was already in the tree.
     *         Otherwise, returns the root node of the tree with
     *         the value added.
     */
    private Node add(Node root, int value) {
        if(root == null)
            return new Node(value);
        if(value == root.value)
            return null;

        Node aux;
        if(value > root.value) {
            aux = add(root.right, value);
            if(aux == null)
                return null;
            root.setRight(aux);
        }
        else {
            aux = add(root.left, value);
            if(aux == null)
                return null;
            root.setLeft(aux);
        }
        return balance(root);
    }

    /**
     *
     * @param value Integer value to remove from the tree.
     * @return Returns false if the value was not in the tree.
     *         Otherwise, returns true.
     */
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
        return oldQty != nodeQty;
    }

    /**
     *
     * @param root Root node of the tree where we want to remove the value.
     * @param value Integer value to remove from the tree.
     * @return Returns null if the value was not in the tree.
     *         Otherwise, returns the root node of the tree with
     *         the value removed.
     */
    private Node remove(Node root,int value) {
        if(root.value == value){
            nodeQty--;
            if(root.right!=null) {
                Node aux = minimum(root.right);
                aux.setLeft(root.left);
                if(aux.value != root.right.value)
                    aux.setRight(root.right);
                return balance(aux);
            }
            else if(root.left!=null) {
                Node aux = maximum(root.left);
                aux.setRight(root.right);
                if(aux.value != root.left.value)
                    aux.setLeft(root.left);
                return balance(aux);
            }
            return null;
        }
        else if(root.value < value && root.right !=null) {
            root.setRight(remove(root.right, value));
        }
        else if(root.value > value && root.left!= null) {
            root.setLeft(remove(root.left,value));
        }
        return balance(root);
    }

    /**
     *
     * @param root Root node of the tree where we want to
     *             find the minimum node.
     * @return Returns the minimum node from the given tree.
     */
    private Node minimum(Node root) {
        if(root.left != null) {
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

    /**
     *
     * @param root Root node of the tree where we want to
     *             find the maximum node.
     * @return Returns the maximum node from the given tree.
     */
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

    /**
     * Makes the left rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.setRight(newRoot.left);
        newRoot.setLeft(root);
        return newRoot;
    }

    /**
     * Makes the right rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.setLeft(newRoot.right);
        newRoot.setRight(root);
        return newRoot;
    }

    /**
     * Makes the left-right rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateLeftRight(Node root) {
        root.setLeft(rotateLeft(root.left));
        return rotateRight(root);
    }

    /**
     * Makes the right-left rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateRightLeft(Node root) {
        root.setRight(rotateRight(root.right));
        return rotateLeft(root);
    }

    /**
     * Balances the given tree.
     *
     * @param root The root of the tree to be balance
     * @return Returns the root of the balance tree
     */
    private Node balance(Node root) {
        int bf = root.getBalanceFactor();
        if (bf > 1) {
            /* Left-Left case */
            if (root.left.getBalanceFactor() >= 0)
                return rotateRight(root);
            /* Left-Right case */
            else
                return rotateLeftRight(root);
        }
        if (bf < -1) {
            /* Right-Right case */
            if (root.right.getBalanceFactor() <= 0)
                return rotateLeft(root);
            /* Right-Left case */
            else
                return rotateRightLeft(root);
        }
        return root;


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

    private void print(Node root, String prev, boolean last) {
        if(root!=null) {
            if(last) {
                prev=prev.concat("\\- ");
            }
            else {
                prev=prev.concat("|- ");
            }
            System.out.println(prev+root.value);
        }
    }
    
    private void printTree(Node root, String prev){
        boolean right=root.right==null;
        boolean left=root.left==null;
        if(!right){
            if(!left) {
                print(root.right, prev, false);          //puede cambiar
                printTree(root.right, prev+"|  ");
                print(root.left, prev, true);
                printTree(root.left,prev+"   ");
            }else{
                print(root.right, prev, true);
                printTree(root.right,prev+"   ");
            }
        }else{
            if(!left){
                print(root.left, prev, true);
                printTree(root.left,prev+"   ");
            }
        }
    }


}
