package BackEnd;

import BackEnd.HashFunction;

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

        /*public String toString()
        {
            return blockId + " : " + data;
        }*/
    }

  public void add(String data)
    {
        long index = (last == null) ? 1 : last.index + 1;
        String previous = (last == null) ? "0000000000000" : last.hash;
        long nonce=0;
        String hash = null;
        //String hash = "0000AAAAAA"; //hay que generarlo
        last = new Block(index, nonce, data, previous, hash, last);
        last.hash = hashData(zeros);
        //System.out.println("El hash es: " + hash);

    }

    public String hashData( int zeros){
        String hash;
        do {
            last.nonce++;
            String message = last.data + "" + last.index + "" + last.previous + "" + last.nonce;
            hash = hashingMethod.hashData(message);
        }while(!isValid(hash,zeros));
        return hash;
    }

    private boolean isValid(String hash, int zeros) {
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

    public int count() //Esto no hace nada? y rompetodo?
    {
        return count(last);
    }

    private int count(Block current)
    {
        if(current == null)
            return 0;
        return 1 + count(current.previousBlock);
    }

    public boolean validate(){
        Block curr = last;
        Block prev = null;
        while(curr.index > 1){
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
        String data = readDataFromFile(filePath);
        //cambio la data del bloque por la que acabo de generar

    }

    private String readDataFromFile(String filePath){
        BufferedReader br = null;
        FileReader fr = null;
        String data = "";

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                data += sCurrentLine;
            }
            System.out.println("La data leida fue:\n\t" + data);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
        return data;
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
