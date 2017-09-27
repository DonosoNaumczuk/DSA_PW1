/**
 * Created by brothers on 26/09/2017.
 */
public class Master {
    private BlockChain blockChain;
    private AVLTree avlTree;

    public Master(int zeros) throws Exception {
        if(zeros <= 0)
            throw new Exception("Error, la cantidad de ceros debe ser positiva");

        this.blockChain = new BlockChain(zeros);
        this.avlTree = new AVLTree();
    }

    public void add(int number)
    {
        if(avlTree.add(number))
            blockChain.add("Insert " + number);
        else
            blockChain.add("Insertion failed");
    }

    public void remove(int number)
    {
        //if(avlTree.remove(number))
        blockChain.add("Remove " + number);
        //else
        //blockChain.add("Removal failed");
    }

    public int[] lookup(int number)
    {
        //Not implemented
        return new int[0];
    }

    public boolean validate()
    {
        //Not implemented
        return true;
    }

    public static void main(String[] args) {
        /*BlockChain b = new BlockChain(4);

        b.add("Hola");
        b.add("Como");
        b.add("Estas");

        System.out.println(b);*/
    }
}
