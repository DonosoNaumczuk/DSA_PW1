package Test;

import BackEnd.BlockChain;
import BackEnd.HashFunction;
import BackEnd.SHA256;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HashTest{
    private BlockChain blockChain;

    @Before
    public void Before() {
        HashFunction hashFunction = new SHA256();
        blockChain = new BlockChain(4, hashFunction);
        blockChain.add(6);
        blockChain.add(9);
    }

    @Test
    public void hashingWithFourZeros() {
        long lastIndex = blockChain.blockQty();
        String hash = blockChain.mine(blockChain.getBlock(lastIndex),4);
        String result = "";
        System.out.println(hash);
        for(int i = 0; i < 4; i++)
            result += hash.charAt(i);
        assertEquals("Must have 4 zeros at begin.", "0000", result);
    }

    @Test
    public void hashingWithFiveZeros() {
        long lastIndex = blockChain.blockQty();
        String hash = blockChain.mine(blockChain.getBlock(lastIndex), 5);
        String result = "";
        for(int i = 0; i < 5; i++)
            result += hash.charAt(i);
        assertEquals("Must have 5 zeros at begin.", "00000", result);
    }

    /*
     * We only test from 4 to 6 zeros because more than 6 takes a lot of time for mining.
     */
    @Test
    public void hashingWithSixZeros(){
        long lastIndex = blockChain.blockQty();
        String hash = blockChain.mine(blockChain.getBlock(lastIndex), 6);
        String result = "";
        for(int i = 0; i < 6; i++)
            result += hash.charAt(i);
        assertEquals("Must have 6 zeros at begin.", "000000", result);
    }
}
