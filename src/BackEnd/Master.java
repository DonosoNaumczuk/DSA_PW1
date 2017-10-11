package BackEnd;

import BackEnd.AVLTree;
import BackEnd.BlockChain;
import BackEnd.HashFunction;
import BackEnd.SHA256;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;

public class Master {
    private BlockChain blockChain;
    private AVLTree avlTree;
    private HashFunction hash;

    public Master(int zeros) throws Exception {
        if(zeros <= 0)
            throw new Exception("Error, la cantidad de ceros debe ser positiva");
        hash = new SHA256();
        blockChain = new BlockChain(zeros, hash);
        avlTree = new AVLTree();
    }

    private static final String prints[]={"Adios","Error, comando o parametro invalido",
                                          "Se realizo la accion"};

    private static final int EXIT = 0;
    private static final int COMMAND_ERROR = 1;
    private static final int NO_ERROR = 2;

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
            add(Integer.parseInt(s.substring(4,s.length())));
        }
        else if (s.matches(filter[1])) {
            remove(Integer.parseInt(s.substring(7,s.length())));
        }
        else if (s.matches(filter[2])) {
            lookup(Integer.parseInt(s.substring(7,s.length())));
        }
        else if (s.matches(filter[3])) {
            validate();
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
    private static int getNumber(char c[], int first, int last){
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
    private void add(int number)
    {
        if(avlTree.add(number))
            blockChain.add("Insert " + number);
        else
            blockChain.add("Insertion failed");
    }

    /**
     * It execute the remove of the tree and blockchain.
     *
     * @param number the number to be remove
     */
    private void remove(int number)
    {
        if(avlTree.remove(number))
            blockChain.add("Remove " + number);
        else
            blockChain.add("Removal failed");
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
