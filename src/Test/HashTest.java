package Test;

import BackEnd.BlockChain;
import BackEnd.HashFunction;
import BackEnd.SHA256;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class HashTest{
   // private BlockChain b;
    @Before
    public void Before() {
        HashFunction f = new SHA256();
        //b = new BlockChain(4,f);
        //b.add("Test");
    }

    @Test
    void hashingWithFourZeros(){
       // String hash = b.hashData(4);
        //String result = "";
        //for(int i = 0; i < 4; i++)
          //  result += hash.charAt(i);
        //System.out.println(result);
        //assertEquals("Deberia tener cuatro ceros al comienzo.","0000",result);
    }
}
