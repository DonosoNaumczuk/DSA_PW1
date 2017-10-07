import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Master {
    private BlockChain blockChain;
    private AVLTree avlTree;

    public Master(int zeros) throws Exception {
        if(zeros <= 0)
            throw new Exception("Error, la cantidad de ceros debe ser positiva");

        this.blockChain = new BlockChain(zeros);
        this.avlTree = new AVLTree();
    }

    public void run(){//falta el ciclo
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int error;
        String input = new String();
        error = 0;
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        error = command(input);
        //printError(error);
    }

    private static String fliter[]={"add [0-9]+","remove [0-9]+", "lookup [0-9]+", "validate", "modify "};

    //valida y ejecuta
    public int command(String s){   //se podra hacer mejor?
        int aux = 0;
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

        return aux;
    }

    public static int getNumber(char c[],int first, int last){
        int aux=c[first]-'0';
        while (first<last){
            first++;
            aux=aux*10+c[first]-'0';
        }
        return aux;
    }

    public void add(int number)
    {
        if(avlTree.add(number))
            blockChain.add("Insert " + number);
        else
            blockChain.add("Insertion failed");
    }

    public void remove(int number)
    {
        //if(avlTree.remove(number))
        blockChain.add("Remove " + number);
        //else
        //blockChain.add("Removal failed");
    }

    public int[] lookup(int number)
    {
        //Not implemented
        return new int[0];
    }

    public boolean validate()
    {
        //Not implemented
        return true;
    }

    public static void main(String[] args) {
        /*BlockChain b = new BlockChain(4);

        b.add("Hola");
        b.add("Como");
        b.add("Estas");

        System.out.println(b);*/
    }
}
