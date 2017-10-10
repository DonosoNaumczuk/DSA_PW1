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
        String input = null;
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

    private static final String filter[]={"add [0-9]+","remove [0-9]+",
                                          "lookup [0-9]+", "validate",
                                          "modify ","exit"};

    /*
    *  Validates the string and if they are valid it execute the right command.
    *  It return 0 if the command is exit, 2 if it is add, remove, lookup,
    *  validate or modify and 1 if it is invalid.
    */
    private int command(String s) {
        int aux = NO_ERROR;
        if (s.matches(filter[0])) {
            add(getNumber(s.toCharArray(),4, s.length()));
        }
        else if (s.matches(filter[1])) {
            remove(getNumber(s.toCharArray(),7, s.length()));
        }
        else if (s.matches(filter[2])) {
            lookup(getNumber(s.toCharArray(),7, s.length()));
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
    //nose donde moverla pero para mi hay que sacarla de aca
    private static int getNumber(char c[], int first, int last){
        int aux=c[first]-'0';
        while (first<last){
            first++;
            aux=aux*10+c[first]-'0';
        }
        return aux;
    }

    private void add(int number)
    {
        if(avlTree.add(number))
            blockChain.add("Insert " + number);
        else
            blockChain.add("Insertion failed");
    }

    private void remove(int number)
    {
        if(avlTree.remove(number))
            blockChain.add("Remove " + number);
        else
            blockChain.add("Removal failed");
    }

    private int[] lookup(int number)
    {
        //Not implemented
        return new int[0];
    }

    private boolean validate()
    {
        return blockChain.validate();
    }

    public static void main(String[] args) {  //revisar
        int zeros = 4;
        Master m;
        if(args.length<2 && args[0].matches("zero") && args[1].matches("[0-9]+"))
            zeros = getNumber(args[1].toCharArray(),0, args[1].length());
        else
            System.out.println("Input invalido, se setea la cantidad de ceros a 4");

        try {
            m = new Master(zeros);
            m.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            System.out.println("BlockChain class not found");
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
            System.out.println("AVLTree class not found");
            c.printStackTrace();
            return;
        }
    }
}
