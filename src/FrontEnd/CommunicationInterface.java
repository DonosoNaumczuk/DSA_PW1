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
            throw new Exception("Error, la cantidad de ceros debe ser positiva");
        blockChain = new BlockChain(zeros, new SHA256());
    }

    private static final String prints[]={"Adios","Error, comando o parametro invalido",
                                          "Se realizo la accion", "Error, la blockchain es invalida",
                                          "La blockchain es valida"};

    private static final int EXIT = 0;
    private static final int COMMAND_ERROR = 1;
    private static final int NO_ERROR = 2;
    private static final int INVALID_BLOCKCHAIN = 3;
    private static final int VALID_BLOCKCHAIN = 4;

    /** Controls the comunication with the user and the backend */
    public void run() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int print_id;
        String input;
        boolean flag = true;
        while (flag) {
            System.out.println("Esperando comandos:");
            input = br.readLine();
            print_id = command(input);
            if(print_id!=1)
                System.out.println(prints[print_id]);
            if (print_id == EXIT){
                saveBlockchain();
                flag = false;
            }
        }
    }

    private static final String filter[]={"add -?[0-9]+","remove -?[0-9]+",
                                          "lookup -?[0-9]+", "validate",
                                          "modify [0-9]+","exit"};

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
            if(!blockChain.add(Integer.parseInt(s.substring(4,s.length()))))
                aux = INVALID_BLOCKCHAIN;
            else {
                printBlock(0);
            }

        }
        else if (s.matches(filter[1])) {
            if(!blockChain.remove(Integer.parseInt(s.substring(7,s.length()))))
                aux = INVALID_BLOCKCHAIN;
            else {
                printBlock(1);
            }
        }
        else if (s.matches(filter[2])) {
            Set<Long> indexes = blockChain.lookUp(Integer.parseInt(s.substring(7,s.length())));
            if(indexes == null)
                System.out.println("Retorna vacio y crea el siguiente bloque: ");
            else {
                System.out.println("Retorna: ");
                System.out.println(indexes);
                System.out.println("y crea el siguiente bloque: ");
            }
            printBlock(2);
        }
        else if (s.matches(filter[3])) {
            if(!blockChain.validate())
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

    /** Saves the blockchain in the blockchain.ser file */
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
     * Loads the blockchain from the blockchain.ser file
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

    public void printBlock(int op){
        String value = "";
        System.out.println("Indice: " + blockChain.blockQty());
        System.out.println("Nonce: " + blockChain.getNonce());
        if(op == 2)
            value = " - true";
        System.out.println("Dato " + blockChain.getData().getOperation() + value);
        System.out.println("Hash: " + blockChain.getHash());
        System.out.println("Ref: " + blockChain.getPrevious());
        if(op ==  0 || op == 1) {
            System.out.println("Y se mantiene el siguiente arbol: ");
            TreePrinter.print(blockChain.getTree().getRoot());
            //print tree
        }
    }
}
