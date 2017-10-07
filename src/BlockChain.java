public class BlockChain {
    private Block last;
    private int zeros;
    private HashFunction hashingMethod;

    public BlockChain(int zeros, HashFunction hashingMethod) {
        this.zeros = zeros;
        this.hashingMethod = hashingMethod;
    }

    private static class Block {
        private long index;
        private long nonce;
        private String data;
        private String previous;
        private Block previousBlock;
        private String hash;

        public Block(long index, long nonce, String data, String previous, String hash, Block previousBlock)
        {
            this.index = index;
            this.nonce = nonce;
            this.data = data;
            this.previous = previous;
            this.hash = hash;
            this.previousBlock = previousBlock;

        }

    }

        /*public String toString()
        {
            return blockId + " : " + data;
        }*/


    public void add(String data)
    {
        long index = (last == null) ? 1 : last.index + 1;
        String previous = (last == null) ? "0000000000000" : last.hash;
        long nonce=0;
        String hash;
        //String hash = "0000AAAAAA"; //hay que generarlo
        do {
            nonce++;
            String message = data + "" + index + "" + previous + "" + nonce;
            hash = hashingMethod.hashData(message);
        }while(!isValid(hash));
        //System.out.println("El hash es: " + hash);
            /*nonce = 1;
        while(!isValid(hash = getHash(blockId, nonce, data, previous)))
            nonce ++;*/

        last = new Block(index, nonce, data, previous, hash, last);
    }

    private boolean isValid(String hash) {
        for(int i = 0; i < zeros; i++)
        {
            if(hash.charAt(i) != '0')
                return false;
        }
        return true;
    }

    /*private String getHash(long blockId, long nonce, String data, String previous) {
        return String.valueOf(((Long)blockId).hashCode() + ((Long)nonce).hashCode() + data.hashCode() + previous.hashCode());
    }*/

    public int count()
    {
        return count(last);
    }
    private int count(Block current)
    {
        if(current == null)
            return 0;
        return 1 + count(current.previousBlock);
    }

    /*public String toString()
    {
        String blocks = "";
        Block current = last;

        while(current != null)
        {
            blocks += current.toString() + "\n";
            current = current.previousBlock;
        }
        return blocks;
    }*/
}
