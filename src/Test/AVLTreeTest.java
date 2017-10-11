package Test;

import BackEnd.AVLTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AVLTreeTest {
    private AVLTree tree;
    @Before
    public void Before() {
        tree = new AVLTree();
    }

    @Test
    public void addTest(){
        AVLTree t = new AVLTree();
        t.add(10);
        assertEquals("Deberia tener un nodo",1,t.getNodeQty());

    }
}
