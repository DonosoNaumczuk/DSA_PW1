package FrontEnd;

import BackEnd.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.util.Set;

public class CommunicationInterface {
    private BlockChain blockChain;

    public CommunicationInterface(int zeros) throws Exception {
        if(zeros <= 0)
            throw new Exception("Error, the zero quantity must be positive");
        blockChain = new BlockChain(zeros, new SHA256());
    }

    private static final String prints[]={"Goodbye!","Error, invalid parameter or command",
                                          "Operation", "Error, invalid blockchain",
                                          "The blockchain is valid"};

    private static final int EXIT = 0;
    private static final int COMMAND_ERROR = 1;
    private static final int NO_ERROR = 2;
    private static final int INVALID_BLOCKCHAIN = 3;
    private static final int VALID_BLOCKCHAIN = 4;

    public BlockChain getBlockChain() {
        return blockChain;
    }

    /**
     * Controls the communication with the user and the backend
     */
    public void run() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int print_id;
        String input;
        boolean flag = true;
        while (flag) {
            System.out.println("Waiting for commands:");
            input = br.readLine();
            print_id = command(input);
            if(print_id!=2)
                System.out.println(prints[print_id]);
            if (print_id == EXIT){
                saveBlockchain();
                flag = false;
            }

        }
    }

    private static final String filter[]={"add -?[0-9]+","remove -?[0-9]+",
                                          "lookup -?[0-9]+", "validate",
                                          "modify [0-9]+ .*","exit"};

    /**
     *  Validates the string and if they are valid it execute the right command.
     *
     *  @param s the string to validate
     *  @return  0 if the command is exit, 2 if it is add, remove, lookup,
     *           validate or modify and 1 if it is invalid.
     */
    private int command(String s) throws IOException{
        int aux = NO_ERROR;
        if (s.matches(filter[0])) {
            int value = Integer.parseInt(s.substring(4,s.length()));
            if(!blockChain.add(value))
                aux = INVALID_BLOCKCHAIN;
            else {
                if(blockChain.getLastBlockData().wasModified())
                    System.out.println("Adds the element " + value + " to the tree");
                else
                    System.out.println("The value " + value + " was already in the tree");
                System.out.println("Adds the following block to the blockchain:");
                printBlock(0);
            }

        }


        else if (s.matches(filter[1])) {
            int value = Integer.parseInt(s.substring(7,s.length()));
            if(!blockChain.remove(value))
                aux = INVALID_BLOCKCHAIN;
            else {
                if(blockChain.getLastBlockData().wasModified())
                    System.out.println("Removes the Node " + value + " from the tree");
                else
                    System.out.println("The value " + value + " was not in the tree");
                System.out.println("Adds the following block to the blockchain:");
                printBlock(1);
            }
        }
        else if (s.matches(filter[2])) {
            Set<Long> indexes = blockChain.lookUp(Integer.parseInt(s.substring(7,s.length())));
            System.out.print("Returns: ");
            if(indexes == null)
                System.out.println("Empty index list");
            else
                System.out.println(indexes);
            System.out.println("Adds the following block to the blockchain:");
            printBlock(2);
        }
        else if (s.matches(filter[3])) {
            if(!blockChain.validate())
                aux = INVALID_BLOCKCHAIN;
            else
                aux = VALID_BLOCKCHAIN;
        }
        else if (s.matches(filter[4])) {
            String[] str = s.split(" ");
            if (str.length == 3 && str[1].matches("[0-9]+")) {
                try {
                    blockChain.modify(Integer.parseInt(str[1]), str[2]);
                }
                catch(Exception e) {
                    System.out.println("Invalid command.");
                }
            }
            else
                aux = COMMAND_ERROR;
        }
        else if (s.matches(filter[5])) {
            aux = EXIT;
        }
        else
            aux = COMMAND_ERROR;
        return aux;
    }

    /**
     *  Saves the Blockchain in the blockchain.ser file
     */
    public void saveBlockchain() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("blockchain.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(blockChain);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in blockchain.ser");
        }
        catch(IOException i) {
            i.printStackTrace();
        }
    }


    /**
     * Loads the Blockchain from the blockchain.ser file
     *
     * @return true if there is a previous blockchain and false otherwise
     */
    public boolean loadBlockchain() {
        File f = new File("blockchain.ser");
        if(f.exists() && !f.isDirectory()){
            try {
                FileInputStream fileIn = new FileInputStream("blockchain.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                blockChain = (BlockChain) in.readObject();
                in.close();
                fileIn.close();
                return true;
            }
            catch(IOException i) {
                i.printStackTrace();
            }
            catch(ClassNotFoundException c) {
                System.out.println("BackEnd.BlockChain class not found");
                c.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Prints the last Block added to the Blockchain.
     *
     * @param op
     */
    public void printBlock(int op){
        String value = "";
        System.out.println("Index: " + blockChain.blockQty());
        System.out.println("Nonce: " + blockChain.getNonce());
        if(op == 2)
            value = " - " + blockChain.getLastBlockData().wasModified();
        System.out.println("Data " + blockChain.getLastBlockData().getOperation() + value);
        System.out.println("Hash: " + blockChain.getLastHash());
        System.out.println("Ref: " + blockChain.getLastPrevious());
        if(op ==  0 || op == 1) {
            System.out.println("That's the state of the tree after the operation: ");
            TreePrinter.print(blockChain.getTree().getRoot());
            System.out.println("");
        }
    }
}
