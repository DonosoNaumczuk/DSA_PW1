package Test;

import BackEnd.AVLTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AVLTreeTest {
    private AVLTree tree;
//    @Before
//    public void Before() {
//        tree = new AVLTree();
//    }

    @Test
    public void addingNodeTest(){
        AVLTree t = new AVLTree();
        t.add(10);
        assertEquals("Deberia tener un nodo.",1,t.getNodeQty());
    }

    @Test
    public void addingNodeCorrectlyTest(){
        AVLTree t = new AVLTree();
        boolean added = t.add(10);
        assertEquals("Deberia tener un nodo.",1,t.getNodeQty());
        assertEquals("Deberia haber dado true.",true,added);
    }

    @Test
    public void addingAnExistingNodeTest(){
        AVLTree t = new AVLTree();
        t.add(10);
        t.add(5);
        boolean added = t.add(5);
        assertEquals("Deberia tener dos nodos.",2,t.getNodeQty());
        assertEquals("Deberia haber dado false.",false,added);
    }



    @Before
    public void before(){
        tree = new AVLTree();
        tree.add(5);
        tree.add(9);
        tree.add(100);
        tree.add(14);
        tree.add(-10);
        tree.add(10);
    }

    @Test
    public void addingBalancingTreeTest(){
        tree.add(1000);
        assertEquals("Deberia estar balanceado.",true,tree.isBalanced());
    }

    @Test
    public void succesfulEqualsTreeTest(){
        AVLTree t = tree;
        assertEquals("Deberian ser iguales los arboles.",true,tree.equals(t));
        AVLTree t2 = new AVLTree();
        t2.add(9);
        t2.add(14);
        t2.add(-10);
        t2.add(5);
        t2.add(10);
        t2.add(100);
        assertEquals("Deberian ser iguales los arboles.",true,tree.equals(t));
    }

    @Test
    public void unsuccesfulEqualsTreeTest(){
        AVLTree t = new AVLTree();
        t.add(9);
        t.add(14);
        t.add(-10);
        t.add(5);
        t.add(10);
        t.add(100);
        t.add(1);
        assertEquals("Deberian ser distintos los arboles.",false,tree.equals(t));
        tree.add(1);
        tree.add(104); //with an extra right child
        assertEquals("Deberian ser distintos los arboles.",false,tree.equals(t));
        t.add(104);
        tree.add(-14);//with an extra left child
        assertEquals("Deberian ser distintos los arboles.",false,tree.equals(t));
        AVLTree t2 = new AVLTree();
        t2.add(10);
        t2.add(16);
        assertEquals("Deberian ser distintos los arboles.",false,tree.equals(t2));
    }

    @Test
    public void removingNodeTest(){
        int qty = tree.getNodeQty();
        tree.remove(10);
        assertEquals("Deberia tener un nodo menos.",qty-1,tree.getNodeQty()); //see if only one node removed
        boolean value =tree.add(10); //see if 10 was removed
        assertEquals("10 no deberia estar en el arbol.",true,value);
    }

    @Test
    public void removingNodeCorrectlyTest(){
        int qty = tree.getNodeQty();
        boolean removed = tree.remove(10);
        assertEquals("Deberia tener un nodo menos.",qty-1,tree.getNodeQty());
        assertEquals("Deberia haber dado true.",true,removed);
    }

    @Test
    public void removingNoneExistingNodeTest(){
        int qty = tree.getNodeQty();
        boolean removed = tree.remove(-1);
        assertEquals("Deberia dar la misma cantidad de nodos.",qty,tree.getNodeQty());
        assertEquals("Deberia haber dado false.",false,removed);
    }



}
