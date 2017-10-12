package Test;

import BackEnd.AVLTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AVLTreeTest {

    private AVLTree beforeTree;

    @Before
    public void before() {
        beforeTree = new AVLTree();
        beforeTree.add(5);
        beforeTree.add(9);
        beforeTree.add(100);
        beforeTree.add(14);
        beforeTree.add(-10);
        beforeTree.add(10);
    }

    @Test
    public void addingExistingNodeTest() {
        AVLTree tree = new AVLTree();
        tree.add(10);
        tree.add(5);
        boolean returnedValue = tree.add(5);
        assertEquals("Must have two nodes.", 2, tree.getNodeQty());
        assertEquals("Must be false.", false, returnedValue);
    }

    @Test
    public void addingNoneExistingNodeTest() {
        AVLTree tree = new AVLTree();
        tree.add(10);
        tree.add(2);
        boolean returnedValue = tree.add(5);
        assertEquals("Must have three nodes.", 3, tree.getNodeQty());
        assertEquals("Must be true.", true, returnedValue);
    }

    @Test
    public void balanceTreeTest() {
        AVLTree tree = beforeTree;
        tree.add(1000);
        assertEquals("Must be balanced.", true, tree.isBalanced());
    }

    @Test
    public void comparingEqualTreesTest() {
        AVLTree tree1 = beforeTree;
        AVLTree tree2 = new AVLTree();
        tree2.add(9);
        tree2.add(14);
        tree2.add(5);
        tree2.add(-10);
        tree2.add(10);
        tree2.add(100);
        assertEquals("Equals method must return true.", true, tree1.equals(tree2));
    }

    @Test
    public void comparingNotEqualTreesTest() {
        AVLTree tree1 = new AVLTree();
        tree1.add(9);
        tree1.add(14);
        tree1.add(-10);
        tree1.add(5);
        tree1.add(10);
        tree1.add(100);
        tree1.add(1);
        assertEquals("Equals method must return false.", false, beforeTree.equals(tree1));
        beforeTree.add(1);
        beforeTree.add(104);  /* With an extra right child */
        assertEquals("Equals method must return false.", false, beforeTree.equals(tree1));
        tree1.add(104);
        beforeTree.add(-14); /* With an extra left child */
        assertEquals("Equals method must return false.", false, beforeTree.equals(tree1));
        AVLTree tree2 = new AVLTree();
        tree2.add(10);
        tree2.add(16);
        assertEquals("Equals method must return false.", false, beforeTree.equals(tree2));
    }

    @Test
    public void removingExistingNodeTest() {
        AVLTree tree = beforeTree;
        int beforeQty = tree.getNodeQty();
        tree.remove(10);
        int currentQty = tree.getNodeQty();
        assertEquals("Deberia tener un nodo menos.", beforeQty - 1, currentQty);
        boolean wasAdded = tree.add(10);
        assertEquals("Variable wasAdded must be true.", true, wasAdded);
    }


    @Test
    public void removingNoneExistingNodeTest() {
        AVLTree tree = beforeTree;
        int beforeQty = tree.getNodeQty();
        boolean wasRemoved = tree.remove(-1);
        int currentQty = tree.getNodeQty();
        assertEquals("Node quantity must be equal.", currentQty, beforeQty);
        assertEquals("Variable wasRemoved must be false.", false, wasRemoved);
    }

}
