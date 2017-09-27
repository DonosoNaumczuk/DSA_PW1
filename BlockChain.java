/**
 * Created by brothers on 26/09/2017.
 */
public class BlockChain {
    private Node last;
    private int zeros;

    public BlockChain(int zeros)
    {
        this.zeros = zeros;
    }

    private static class Node {
        private long block;
        private long nonce;
        private String data;
        private String previous;
        private Node previousNode;
        private String hash;

        public Node(long block, long nonce, String data, String previous, String hash, Node previousNode)
        {
            this.block = block;
            this.nonce = nonce;
            this.data = data;
            this.previous = previous;
            this.hash = hash;
            this.previousNode = previousNode;
        }

        /*public String toString()
        {
            return block + " : " + data;
        }*/
    }

    public void add(String data)
    {
        long block = (last == null) ? 1 : last.block + 1;
        String previous = (last == null) ? "0000000000000" : last.hash;
        long nonce = 123;
        String hash = "0000AAAAAA";

        /*nonce = 1;
        while(!isValid(hash = getHash(block, nonce, data, previous)))
            nonce ++;*/

        last = new Node(block, nonce, data, previous, hash, last);
    }

    private boolean isValid(String hash) {
        for(int i = 0; i < zeros; i++)
        {
            if(hash.charAt(i) != '0')
                return false;
        }
        return true;
    }

    /*private String getHash(long block, long nonce, String data, String previous) {
        return String.valueOf(((Long)block).hashCode() + ((Long)nonce).hashCode() + data.hashCode() + previous.hashCode());
    }*/

    public int count()
    {
        return count(last);
    }
    private int count(Node current)
    {
        if(current == null)
            return 0;
        return 1 + count(current.previousNode);
    }

    /*public String toString()
    {
        String blocks = "";
        Node current = last;

        while(current != null)
        {
            blocks += current.toString() + "\n";
            current = current.previousNode;
        }
        return blocks;
    }*/
}
