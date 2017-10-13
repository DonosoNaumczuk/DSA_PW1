package BackEnd;

import FrontEnd.TreePrinter;
import java.util.HashSet;
import java.util.Set;

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
    public boolean equals(Object obj) {
        if(obj == null || !(obj.getClass().equals(getClass())))
            return false;
        AVLTree tree = (AVLTree)obj;
        return compareTree(root, tree.root);
    }
    /**
     *
     * @param value is the value wich set of modifierBlocks we want
     * @return Returns the set of the modifier Blocks if the value isin the tree
     *         Otherwise, returns null
     */
    public Set<Long> getModifiersBlocks(int value){
        Node n = search(root, value);
        return (n!=null)? n.getModifiersBlocks(): null;
    }

    /**
     * @param n is the root of the tree in wich we search for a value
     * @param value is the value that we want to find in the tree
     * @return Returns the node corresponding to the given value if
     *         we find it in the tree.
     *         Otherwise, returns null
     */
    private Node search(Node n, int value){
        if(n==null)
            return null;
        if(n.getValue() == value)
            return n;
        if(n.getValue()>value)
            return search(n.getLeft(),value);
        else
            return search(n.getRight(),value);

    }
    /**
     *
     * @param n1 Node corresponding to the tree of the class.
     * @param n2 Node corresponding to the tree to wich we are
     *           comparing the tree of the class.
     * @return Returns false if subtrees of the given nodes are diferent
     *         Otherwise, returns true
     */
    private boolean compareTree(Node n1,Node n2) {
        if((n1 == null && n2!= null) || (n2 == null && n1!= null) || n1.value != n2.value)
            return false;
        if(n1.getLeft()==null && n2.getLeft()==null && n1.getRight()==null && n2.getRight()==null)
            return n1.value == n2.value;
        if(n1.getLeft()==null && n2.getLeft()!=null || (n1.getLeft()!=null && n2.getLeft()==null))
            return false;
        if(n1.getRight()==null && n2.getRight()!=null || (n1.getRight()!=null && n2.getRight()==null))
            return false;

        boolean leftvalue = true;
        if(n1.getLeft()!=null)
            leftvalue = compareTree(n1.getLeft(),n2.getLeft());
        boolean  rightvalue = true;
        if(n1.getRight()!=null)
            rightvalue = compareTree(n1.getRight(),n2.getRight());

        return (leftvalue && rightvalue && n1.value == n2.value);
    }

    /**
     * 
     *  @param value Is the integer value wanted to add to the tree.
     *  @return Returns true if value is not already in the tree.
     *          Otherwise, return false.
     */
    public boolean add(int value, long blockIndex) {
        if(root == null) {
            root = new Node(value);
            root.addModifierBlock(blockIndex);
            return  true;
        }
        Node aux = add(root, value, blockIndex);
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
    private Node add(Node root, int value, long blockIndex) {
        if(value == root.value)
            return null;

        if(value > root.value) {
            if(root.getRight() != null) {
                root.setRight(add(root.getRight(), value, blockIndex));
            }
            Node newRight = new Node(value);
            newRight.addModifierBlock(blockIndex);
            root.setRight(newRight);
            root.addModifierBlock(blockIndex);
        }
        else {
            if(root.getLeft() != null) {
                root.setLeft(add(root.getLeft(), value, blockIndex));
            }
            Node newLeft = new Node(value);
            newLeft.addModifierBlock(blockIndex);
            root.setLeft(newLeft);
            root.addModifierBlock(blockIndex);
        }
        return balance(root, blockIndex);
    }

    /**
     *
     * @param value Integer value to remove from the tree.
     * @return Returns false if the value was not in the tree.
     *         Otherwise, returns true.
     */
    public boolean remove(int value, long blockIndex) {
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
        root = remove(root, value, blockIndex);
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
    private Node remove(Node root, int value, long blockIndex) {
        if(root.value == value) {
            nodeQty--;
            if(root.right!=null) {
                Node aux = minimum(root.right);
                aux.setLeft(root.left);
                if(aux.value != root.right.value)
                    aux.setRight(root.right);
                return balance(aux, blockIndex);
            }
            else if(root.left!=null) {
                Node aux = maximum(root.left);
                aux.setRight(root.right);
                if(aux.value != root.left.value)
                    aux.setLeft(root.left);
                return balance(aux, blockIndex);
            }
            return null;
        }
        else if(root.value < value && root.right !=null) {
            root.setRight(remove(root.right, value, blockIndex));
        }
        else if(root.value > value && root.left!= null) {
            root.setLeft(remove(root.left,value,blockIndex));
        }
        return balance(root, blockIndex);
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
    private Node rotateLeft(Node root, long blockIndex) {
        Node newRoot = root.right;
        root.setRight(newRoot.left);
        newRoot.setLeft(root);
        newRoot.addModifierBlock(blockIndex);
        root.addModifierBlock(blockIndex);
        if(root.getRight() != null)
            root.getRight().addModifierBlock(blockIndex);
        return newRoot;
    }

    /**
     * Makes the right rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateRight(Node root, long blockIndex) {
        Node newRoot = root.left;
        root.setLeft(newRoot.right);
        newRoot.setRight(root);
        newRoot.addModifierBlock(blockIndex);
        root.addModifierBlock(blockIndex);
        if(root.getLeft() != null)
            root.getLeft().addModifierBlock(blockIndex);
        return newRoot;
    }

    /**
     * Makes the left-right rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateLeftRight(Node root, long blockIndex) {
        root.setLeft(rotateLeft(root.left, blockIndex));
        return rotateRight(root, blockIndex);
    }

    /**
     * Makes the right-left rotation.
     *
     * @param root The root of the tree to be rotate
     * @return Returns the root of the rotated tree
     */
    private Node rotateRightLeft(Node root, long blockIndex) {
        root.setRight(rotateRight(root.right, blockIndex));
        return rotateLeft(root, blockIndex);
    }

    /**
     * Balances the given tree.
     *
     * @param root The root of the tree to be balance
     * @return Returns the root of the balance tree
     */
    private Node balance(Node root, long blockIndex) {
        int bf = root.getBalanceFactor();
        if (bf > 1) {
            /* Left-Left case */
            if (root.left.getBalanceFactor() >= 0)
                return rotateRight(root, blockIndex);
            /* Left-Right case */
            else
                return rotateLeftRight(root, blockIndex);
        }
        if (bf < -1) {
            /* Right-Right case */
            if (root.right.getBalanceFactor() <= 0)
                return rotateLeft(root, blockIndex);
            /* Right-Left case */
            else
                return rotateRightLeft(root, blockIndex);
        }
        return root;
    }

    /**
     * Tells if a tree is balanced.
     *
     * @return Returns true if the tree is balanced and false if not.
     */
    public boolean isBalanced(){

        return isBalancedR(root);
    }

    /**
     *
     * @param n The node whose tree we want to know if is balanced
     * @return Returns true if the tree of the given node is balanced
     *          and false if not.
     */
    boolean isBalancedR(Node n){
        if(n == null)
            return true;
        int factor = n.getBalanceFactor();
        if(factor < -1 || factor >1)
            return false;
        boolean lbalance = true;
        boolean rbalance = true;
        if(n.getLeft()!=null)
            lbalance = isBalancedR(n.getLeft());
        if(n.getRight()!=null)
            rbalance = isBalancedR(n.getRight());
        return lbalance && rbalance;
    }

    private static class Node implements TreePrinter.PrintableNode, java.io.Serializable {
        int value;
        int height;
        Node left;
        Node right;
        Set<Long> modifiersBlocks;

        private Node(int value) {
            this.value = value;
            height = 1;
            modifiersBlocks = new HashSet<>();
        }

        public Set<Long> getModifiersBlocks() {
            return modifiersBlocks;
        }

        private void addModifierBlock(long blockIndex) {
            modifiersBlocks.add(blockIndex);
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

        public int getValue() {return value;}

        private void setLeft(Node left) {
            this.left = left;
            computeHeight();
        }

        private void setRight(Node right) {
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

}
