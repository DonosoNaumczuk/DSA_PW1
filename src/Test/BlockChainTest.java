import BackEnd.BlockChain;
import BackEnd.SHA256;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BlockChainTest {
    private BlockChain blockChain;
    @Before
    public void before() {
        blockChain = new BlockChain(3, new SHA256());
    }

    @Test
    public void addOperationTest() {
        blockChain.add(6);
        blockChain.add(9);
        long blockQty = blockChain.blockQty();
        assertEquals("Must have 2 blocks.", 2, blockQty);
    }

    @Test
    public void successfulAddOperation() {
        boolean value = blockChain.add(10);
        assertEquals("Should be true.",true,value);
    }

    @Test
    public void succesfulremoveOperation() {
        boolean value = blockChain.remove(10);
        assertEquals("Should be true.",true,value);
    }

    @Test
    public void lookUpWithAddTest() {
        blockChain.add(5);
        blockChain.add(10);
        Set<Long> indexes = blockChain.lookUp(10);
        Set<Long> indexesExpected = new HashSet<>();
        indexesExpected.add(new Long(2));
        assertEquals("Both Set should be equals.",indexesExpected,indexes);
        indexesExpected.add(new Long(1));
        indexes = blockChain.lookUp(5);
        assertEquals("Both Set should be equals.",indexesExpected,indexes);

    }

    @Test
    public void lookUpwithRotationTest(){
        blockChain.add(5);
        blockChain.add(10);
        blockChain.add(15);
        Set<Long> indexes = blockChain.lookUp(5);
        Set<Long> indexesExpected = new HashSet<>();
        indexesExpected.add(new Long(1));
        indexesExpected.add(new Long(2));
        indexesExpected.add(new Long(3));
        assertEquals("Both Set should be equals.",indexesExpected,indexes);

    }
}
