public class BlockChain {
    private Block last;
    private int zeros;

    public BlockChain(int zeros)
    {
        this.zeros = zeros;
    }

    private static class Block {
        private long blockId;
        private long nonce;
        private String data;
        private String previous;
        private Block previousBlock;
        private String hash;

        public Block(long blockId, long nonce, String data, String previous, String hash, Block previousBlock)
        {
            this.blockId = blockId;
            this.nonce = nonce;
            this.data = data;
            this.previous = previous;
            this.hash = hash;
            this.previousBlock = previousBlock;
        }

        /*public String toString()
        {
            return blockId + " : " + data;
        }*/
    }

    public void add(String data)
    {
        long blockId = (last == null) ? 1 : last.blockId + 1;
        String previous = (last == null) ? "0000000000000" : last.hash;
        long nonce = 123;
        String hash = "0000AAAAAA";

        /*nonce = 1;
        while(!isValid(hash = getHash(blockId, nonce, data, previous)))
            nonce ++;*/

        last = new Block(blockId, nonce, data, previous, hash, last);
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
