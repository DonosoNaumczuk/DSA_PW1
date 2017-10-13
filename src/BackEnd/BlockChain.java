package BackEnd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;
import FrontEnd.NoBlockException;

public class BlockChain implements java.io.Serializable {
    private Block last;
    private int zeros;
    private SHA256 hashingMethod;
    private AVLTree tree;

    public BlockChain(int zeros, SHA256 hashingMethod)
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

        /**
         * Sets index
         *
         * @param index index to be set
         */
        private void setIndex(long index) {
            this.index = index;
        }

        /**
         * Sets data
         *
         * @param data data to be set
         */
        private void setData(Data data) {
            this.data = data;
        }

        /**
         * Gets hash
         *
         * @return hash
         */
        private String getHash(){
            return hash;
        }
    }

    /**
     * Gets the AVLTree
     *
     * @return AVLTree
     */
    public AVLTree getTree(){
        return tree;
    }

    /**
     * Gets the block with th blockindex as the index
     *
     * @param blockIndex index of the block to be return
     * @return block with the blockindex as index
     */
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

    /**
     * Gets the quantity of blocks
     *
     * @return the quantity of blocks
     */
    public long blockQty() {
        return last.index;
    }


    public long getNonce(){
        return last.nonce;
    }

    public Data getLastBlockData(){
        return last.data;
    }

    public String getHash(){
        return last.hash;
    }

    public String getPrevious(){
        return last.previous;
    }
    /**
     * Checks if a index is valid
     *
     * @param blockIndex the index to be check
     * @return true if is valid and false otherwise
     */
    private boolean isValidIndex(long blockIndex) {
        if(last == null)
            throw new NoBlockException();
        return blockIndex <= last.index && blockIndex > 0;
    }

    /**
     * Add the add operation to the block
     *
     * @param value the value to be add
     * @return true if the operation was added and false otherwise
     */
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

    /**
     * Mines a block, in order to get n zeros in his hash
     *
     * @param block the block to be mine
     * @param zeros the quantity of zeros
     * @return hash of the block
     */
    public String mine(Block block, int zeros) {
        String hash;
        AVLTree tree = block.data.getTreeState();
        int nodeQty = tree.getNodeQty();
        do {
            block.nonce++;
            String message = block.data.getOperation() + nodeQty + block.index + block.previous + block.nonce;
            hash = hashingMethod.hashData(message);
        } while(!isValid(hash, zeros));
        return hash;
    }


    /**
     * Adds the remove operation to the blockchain
     *
     * @param value to be remove from the tree
     * @return true if the operation was added and false otherwise
     */
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
    public void modify (int index, String filePath) throws IOException {
        if(!isValidIndex(index))
            throw new IndexOutOfBoundsException("The given index doesn't correspond to the blockchain.");
        Block block = last;
        while(block.index > index) {
            block = block.previousBlock;
        }
        Data data = readDataFromFile(filePath);
        block.setData(data);
        String message = block.data.getOperation() + block.data.getTreeState().getNodeQty() + block.index + block.previous + block.nonce;
        block.hash = hashingMethod.hashData(message);
    }

    /**
     * @param filePath is the absolute path of a file that we want to read
     * @return Returns on a String the content of the file.
     */
    private Data readDataFromFile(String filePath) throws IOException {
        RandomAccessFile fileReader =  new RandomAccessFile(filePath, "r");
        String operation = fileReader.readLine();
        String path = fileReader.readLine();
        boolean modified = true;
        String modifiedStr = fileReader.readLine();
        if(modifiedStr == null || modifiedStr.equals("") || modifiedStr.equals("0")) {
            modified = false;
        }

        return new Data(operation, path, modified);
    }
}
