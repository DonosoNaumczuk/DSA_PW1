package Test;

import BackEnd.BlockChain;
import BackEnd.HashFunction;
import BackEnd.SHA256;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HashTest{
    private BlockChain b;
    @Before
    public void Before() {
        HashFunction f = new SHA256();
        b = new BlockChain(4,f);
        b.add("Test");
    }

    @Test
    public void hashingWithFourZeros(){
         String hash = b.mineHash(4);
        String result = "";
        for(int i = 0; i < 4; i++)
            result += hash.charAt(i);
        assertEquals("Deberia tener cuatro ceros al comienzo.","0000",result);
    }

    @Test
    public void hashingWithFiveZeros(){
        String hash = b.mineHash(5);
        String result = "";
        for(int i = 0; i < 5; i++)
            result += hash.charAt(i);
        assertEquals("Deberia tener cinco ceros al comienzo.","00000",result);
    }

    @Test
    public void hashingWithSixZeros(){
        String hash = b.mineHash(6);
        String result = "";
        for(int i = 0; i < 6; i++)
            result += hash.charAt(i);
        assertEquals("Deberia tener cinco ceros al comienzo.","000000",result);
    }
}
