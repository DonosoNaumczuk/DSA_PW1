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

    private static final String prints[]={"Adios","Error, comando o parametro invalido"};

    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int print_id;
        String input = null;
        boolean flag = true;
        while (flag) {
            try {
                input = br.readLine();
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
        /*BlockChain b = new BlockChain(4);

        b.add("Hola");
        b.add("Como");
        b.add("Estas");

        System.out.println(b);*/
    }
}
