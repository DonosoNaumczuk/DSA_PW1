package BackEnd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import FrontEnd.NoBlockException;

public class BlockChain implements java.io.Serializable {
    private Block last;
    private int zeros;
    private HashFunction hashingMethod;
    private AVLTree tree;

    public BlockChain(int zeros, HashFunction hashingMethod)
    {
        this.zeros = zeros;
        this.hashingMethod = hashingMethod;
        this.tree = new AVLTree();
    }

    private static class Block implements java.io.Serializable {
        private long index;
        private long nonce;
        private Data data;
        private String previous;
        private String hash;
        private Block previousBlock;

        public Block(long index, Data data, String previous, Block previousBlock) {
            this.index = index;
            this.data = data;
            this.previous = previous;
            this.previousBlock = previousBlock;
        }

        private void setIndex(long index) {
            this.index = index;
        }

        private void setData(Data data) {
            this.data = data;
        }

        private String getHash(){
            return hash;
        }
    }

    public AVLTree getTree(){
        return tree;
    }

    public Block getBlock(long blockIndex) {
        if(isValidIndex(blockIndex)) {
            Block block = last;
            while(block.index > blockIndex) {
                block = block.previousBlock;
            }
            return block;
        }
        throw new IndexOutOfBoundsException("The given index:" + blockIndex + "doesn't correspond to the blockchain.");
    }

    public long blockQty() {
        return last.index;
    }

    private boolean isValidIndex(long blockIndex) {
        if(last == null)
            throw new NoBlockException();
        return blockIndex <= last.index || blockIndex > 0;
    }

    public boolean add(int value) {
        if(validate()) {
            long index = (last == null) ? 1 : last.index + 1;
            String previous = (last == null) ? "0000000000000000000000000000000000000000000000000000000000000000" : last.hash;
            boolean wasModified = tree.add(value, index);
            Data data = new Data("ADD", tree, wasModified);
            Block newBlock = new Block(index, data, previous, last);
            newBlock.hash = mine(newBlock, zeros);
            last = newBlock;
            return true;
        }
        else
            return false;

    }

    public String mine(Block block, int zeros) {
        String hash;
        do {
            block.nonce++;
            String message = block.data.getOperation() + block.data.getTreeState().getNodeQty() + block.index + block.previous + block.nonce;
            hash = hashingMethod.hashData(message);
        } while(!isValid(hash, zeros));
        return hash;
    }

    public boolean remove(int value) {
        if(validate()) {
            long index = (last == null) ? 1 : last.index + 1;
            String previous = (last == null) ? "0000000000000000000000000000000000000000000000000000000000000000" : last.hash;
            boolean wasModified = tree.remove(value, index);
            Data data = new Data("REMOVE", tree, wasModified);
            Block newBlock = new Block(index, data, previous, last);
            newBlock.hash = mine(newBlock, zeros);
            last = newBlock;
            return true;
        }
        else
            return false;
    }

    /**
     * @param hash is the hash of the last block.
     * @param zeros is the quantity of zeros at the begining that the hash
     *              should have
     * @return Returns true if the  is valid
     *         Otherwise, returns false
     */
    private boolean isValid(String hash, int zeros) {
        for(int i = 0; i < zeros; i++) {
            if(hash.charAt(i) != '0')
                return false;
        }
        return true;
    }

    /**
     * @return Returns true if the blockchain is valid
     *         Otherwise, returns false
     */
    public boolean validate() {
        Block curr = last;
        Block prev;
        while(last != null && curr.index > 1) {
            prev = curr.previousBlock;
            if((!isValid(curr.getHash(),zeros))||!curr.previous.equals(prev.hash))
                return false;
            curr = prev;
        }
        return true;
    }

    /**
     * @param value is the value that we want to get the index of the blocks
     *              that modified the node corresponding to the value..
     * @return Returns a Set with the indexes of the blocks that modified the node
     *         Otherwise, returns null
     */
    public Set<Long> lookUp(int value) {
        if(validate()) {
            return tree.getModifiersBlocks(value);
        }
        return null;
    }


    /**El readDataFromFile deberia estar en la funcion que llama a modify y pasarle
    directamente la data y el indice pero por ahora la pongo aca
     */
    public void modify (int index, String filePath){
        if(index > last.index || index < 1)
            throw new IndexOutOfBoundsException("The given index doesn't correspond to the blockchain.");
        long i = last.index;
        Block curr = last;
        while(i > index) {
            curr = curr.previousBlock;
            i--;
        }
        Data data = readDataFromFile(filePath); //DATA YA NO ES STRING

        curr.setData(data);
        String message = curr.data.getOperation() + curr.data.getTreeState().getNodeQty() + curr.index + curr.previous + curr.nonce;
        curr.hash = hashingMethod.hashData(message);

        //cambio la data del bloque por la que acabo de generar

    }

    /**
     * @param filePath is the absolute path of a file that we want to read
     * @return Returns on a String the content of the file.
     */
    private Data readDataFromFile(String filePath){
        BufferedReader br = null;
        FileReader fr = null;
        //String data = ""; //LA DATA YA NO ES STRING
        String operation = null;
        //tree = null;
        Boolean treeModified = false;

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            operation = br.readLine();
            if(operation != null) {
               //tree.br.readLine();
                int value;
                value = br.read();
                if(value!= -1)
                    treeModified =(value==0)?false:true;
            }
            //System.out.println("La data leida fue:\n\t" + data);

        }
        catch (IOException e) {

            e.printStackTrace();

        }
        finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            }
            catch (IOException ex) {

                ex.printStackTrace();

            }

        }
        return new Data(operation,tree,treeModified);

    }
}
