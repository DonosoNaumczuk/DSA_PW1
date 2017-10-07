

public class Master {
    public BlockChain blockChain;
    private AVLTree avlTree;
    private HashFunction hash;
    public Master(int zeros) throws Exception {
        if(zeros <= 0)
            throw new Exception("Error, la cantidad de ceros debe ser positiva");

        hash = new SHA256();
        blockChain = new BlockChain(zeros, hash);
        avlTree = new AVLTree();
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

    public static void main(String[] args) throws Exception{
        Master prueba = new Master(4);
        // String data = "hola";
        // prueba.blockChain.add(data);
        // String hash = prueba.blockChain.getLast().getHash();

        /*BlockChain b = new BlockChain(4);

        b.add("Hola");
        b.add("Como");
        b.add("Estas");

        System.out.println(b);*/
    }
}
