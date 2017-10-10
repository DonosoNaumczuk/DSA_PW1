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

    private static final String prints[]={"Adios","Error, comando o parametro invalido"};

    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int print_id;
        String input = null;
        boolean flag = true;
        while (flag) {
            try {
                input = br.readLine();  //sacar el try/catch?
            } catch (IOException e) {
                e.printStackTrace();
            }
            print_id = command(input);
            if (print_id != -1) {
                System.out.println(prints[print_id]);
            }
            if (print_id == 0){
                flag = false;
            }
        }
    }

    private static final String fliter[]={"add [0-9]+","remove [0-9]+",
                                          "lookup [0-9]+", "validate",
                                          "modify ","exit"};

    //valida y ejecuta
    private int command(String s){   //se podra hacer mejor?
        int aux = 1;
        if (s.matches(fliter[0])){
            add(getNumber(s.toCharArray(),4, s.length()));
        }
        if (s.matches(fliter[1])){
            remove(getNumber(s.toCharArray(),7, s.length()));
        }
        if (s.matches(fliter[2])){
            lookup(getNumber(s.toCharArray(),7, s.length()));
        }
        if (s.matches(fliter[3])){
            validate();
        }
        if (s.matches(fliter[4])){
            //validar el path del archivo
        }
        if (s.matches(fliter[5])){
            aux = 0;
        }
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
        //if(avlTree.remove(number))
        blockChain.add("Remove " + number);
        //else
        //blockChain.add("Removal failed");
    }

    private int[] lookup(int number)
    {
        //Not implemented
        return new int[0];
    }

    private boolean validate()
    {
        //Not implemented
        return true;
    }

    public static void main(String[] args) {
       /* int zeros;
        Master m;
        if(args.length<2 && args[0].matches("zero") && args[1].matches("[0-9]+")){
            zeros = getNumber(args[1].toCharArray(),0, args[1].length());
        } else {
            System.out.println("Comando invalido, se setea la cantidad de ceros a 4");
            zeros = 4;
        }
        m = new Master(zeros);
        m.run();*/
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
            System.out.printf("Serialized data is saved in blockchain.ser");
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
            System.out.printf("Serialized data is saved in AVL.ser");
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
