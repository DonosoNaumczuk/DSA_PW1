package BackEnd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BlockChain implements java.io.Serializable {
    private Block last;
    private int zeros;
    private HashFunction hashingMethod;

    public BlockChain(int zeros, HashFunction hashingMethod)
    {
        this.zeros = zeros;
        this.hashingMethod = hashingMethod;
    }

    private static class Block {
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
    }

  public boolean add(Data data) {
        if(validate()) {
            long index = (last == null) ? 0 : last.index + 1;
            String previous = (last == null) ? "0000000000000000000000000000000000000000000000000000000000000000" : last.hash;
            Block newBlock = new Block(index, data, previous, last);
            newBlock.hash = mineHash(newBlock, zeros);
            last = newBlock;
            return true;
        }
        else
            return false;
    }

    public String mineHash(Block block, int zeros) {
        String hash;
        do {
            block.nonce++;
            String message = block.data.getOperation() + block.data.getTreeState().getNodeQty() + block.index + block.previous + block.nonce;
            hash = hashingMethod.hashData(message);
        } while(!isValid(hash, zeros));
        return hash;
    }

    private boolean isValid(String hash, int zeros) {
        for(int i = 0; i < zeros; i++) {
            if(hash.charAt(i) != '0')
                return false;
        }
        return true;
    }

    public boolean validate() {
        Block curr = last;
        Block prev;
        while(last != null && curr.index > 1) {
            prev = curr.previousBlock;
            if(!curr.previous.equals(prev.hash))
                return false;
            curr = prev;
        }
        return true;
    }

    /*El readDataFromFile deberia estar en la funcion que llama a modify y pasarle
    directamente la data y el indice pero por ahora la pongo aca
     */
    public void modify (int index, String filePath){
        if(index > last.index || index < 1)
            return;
        //busco el bloque
        String data = readDataFromFile(filePath); //DATA YA NO ES STRING
        //cambio la data del bloque por la que acabo de generar

    }

    private String readDataFromFile(String filePath){
        BufferedReader br = null;
        FileReader fr = null;
        String data = ""; //LA DATA YA NO ES STRING

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                data += sCurrentLine;
            }
            System.out.println("La data leida fue:\n\t" + data);

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
        return data;
    }
}
