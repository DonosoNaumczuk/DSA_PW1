package Test;

import BackEnd.AVLTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AVLTreeTest {

    @Test
    public void addNonExistingValueTest() {
        AVLTree tree = new AVLTree();
        tree.add(10);
        tree.add(2);
        assertEquals("Must have two nodes.", 2, tree.getNodeQty();
    }

    @Test
    public void returnAddNonExistingValueTest() {
        AVLTree tree = new AVLTree();
        tree.add(2);
        boolean returnedValue = tree.add(10);
        assertEquals(true, returnedValue);
    }

    @Test
    public void addExistingValueTest() {
        AVLTree tree = new AVLTree();
        tree.add(10);
        tree.add(10);
        assertEquals("Must have only one node.", 1, tree.getNodeQty());
    }

    @Test
    public void returnAddExistingValueTest() {
        AVLTree tree = new AVLTree();
        tree.add(2);
        boolean returnedValue = tree.add(2);
        assertEquals(false, returnedValue);
    }

    @Test
    public void removeExistingValueTest() {
        AVLTree tree = new AVLTree();
        tree.add(69);
        tree.add(80);
        tree.add(10);
        tree.remove(80);
        assertEquals("Must have two nodes.", 2, tree.getNodeQty());
    }


}
