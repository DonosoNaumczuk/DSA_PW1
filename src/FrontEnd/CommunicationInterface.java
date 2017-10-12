package FrontEnd;

import BackEnd.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;

public class CommunicationInterface {
    private BlockChain blockChain;

    public CommunicationInterface(int zeros) throws Exception {
        if(zeros <= 0)
            throw new Exception("Error, la cantidad de ceros debe ser positiva");
        blockChain = new BlockChain(zeros, new SHA256());
       // avlTree = new AVLTree();
    }

    private static final String prints[]={"Adios","Error, comando o parametro invalido",
                                          "Se realizo la accion", "Error, la blockchain es invalidad",
                                          "La blockchain es validad"};

    private static final int EXIT = 0;
    private static final int COMMAND_ERROR = 1;
    private static final int NO_ERROR = 2;
    private static final int INVALID_BLOCKCHAIN = 3;
    private static final int VALID_BLOCKCHAIN = 4;

    public void run() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int print_id;
        String input;
        boolean flag = true;
        while (flag) {
            input = br.readLine();
            print_id = command(input);
            System.out.println(prints[print_id]);
            if (print_id == EXIT){
                flag = false;
            }
        }
    }

    private static final String filter[]={"add -?[0-9]+","remove -?[0-9]+",
                                          "lookup -?[0-9]+", "validate",
                                          "modify ","exit"};

    /**
     *  Validates the string and if they are valid it execute the right command.
     *
     *  @param s the string to validate
     *  @return  0 if the command is exit, 2 if it is add, remove, lookup,
     *           validate or modify and 1 if it is invalid.
     */
    private int command(String s) {
        int aux = NO_ERROR;
        if (s.matches(filter[0])) {
            if(!add(Integer.parseInt(s.substring(4,s.length()))))
                aux = INVALID_BLOCKCHAIN;
        }
        else if (s.matches(filter[1])) {
            if(!remove(Integer.parseInt(s.substring(7,s.length()))))
                aux = INVALID_BLOCKCHAIN;
        }
        else if (s.matches(filter[2])) {
            lookup(Integer.parseInt(s.substring(7,s.length())));
        }
        else if (s.matches(filter[3])) {
            if(!validate())
                aux = INVALID_BLOCKCHAIN;
            else
                aux = VALID_BLOCKCHAIN;
        }
        else if (s.matches(filter[4])) {
            //validar el path del archivo
        }
        else if (s.matches(filter[5])) {
            aux = EXIT;
        }
        else
            aux = COMMAND_ERROR;
        return aux;
    }

    /**
     * Calculates a number from a char array
     *
     * @param c     the array where the number is
     * @param first the position from the array where the number start
     * @param last  the position from the array where the number end
     * @return      the number
     */
    private static int getNumber(char c[], int first, int last) {
        int aux=c[first]-'0';
        while (first<last){
            first++;
            aux=aux*10+c[first]-'0';
        }
        return aux;
    }

    /**
     * It execute the add of the tree and blockchain.
     *
     * @param number the number to be add
     */
    private boolean add(int number) {
        String operation;
        Data data;
        if(avlTree.add(number))
            operation ="Insert " + number;
        else
            operation = "Insertion failed.";

       //no se que treestate ponerle
        data = new Data(operation,null);
        return blockChain.add(data);

    }

    /**
     * It execute the remove of the tree and blockchain.
     *
     * @param number the number to be remove
     */
    private boolean remove(int number) {
        Boolean aux;
        String operation;
        Data data;
        if(avlTree.remove(number))
            operation = "Remove " + number;
        else
            operation ="Removal failed";
        //no se que poner en treeState
        data = new Data(operation,null);
        return blockChain.add(data);
    }

    /**
     *
     *
     * @param number the number to look in the blockchain
     * @return       the vector of the index of the block that modify the number
     */
    private int[] lookup(int number)
    {
        //Not implemented
        return new int[0];
    }

    /**
     * Checks that the blockchain is valid
     *
     * @return true if the blockchain is valid and false otherwise
     */
    private boolean validate()
    {
        return blockChain.validate();
    }
    
    public void serializeBlockchain()
    {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("blockchain.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(blockChain);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in blockchain.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public void deserializeBlockchain()
    {
        try {
            FileInputStream fileIn = new FileInputStream("blockchain.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            blockChain = (BlockChain) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("BackEnd.BlockChain class not found");
            c.printStackTrace();
            return;
        }
    }

    public void serializeAVL()
    {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("AVL.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(avlTree);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in AVL.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public void deserializeAVL()
    {
        try {
            FileInputStream fileIn = new FileInputStream("AVL.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            avlTree = (AVLTree) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("BackEnd.AVLTree class not found");
            c.printStackTrace();
            return;
        }
    }
}
