import BackEnd.BlockChain;
import BackEnd.SHA256;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BlockChainTest {

    @Before
    public void before() {
        BlockChain blockChain = new BlockChain(3, new SHA256());
    }

    @Test
    public void addOperationTest() {
        BlockChain blockChain = new BlockChain(3, new SHA256());
        blockChain.add(6);
        blockChain.add(9);
        long blockQty = blockChain.blockQty();
        assertEquals("Must have 2 blocks.", 2, blockQty);
    }
}
