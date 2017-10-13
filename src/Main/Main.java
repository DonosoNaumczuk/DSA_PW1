package Main;

import FrontEnd.CommunicationInterface;

public class Main {
    public static void main(String[] args) {  //revisar
        int zeros = 4;
        CommunicationInterface m;
        if(args.length==2 && args[0].matches("zero") && args[1].matches("[0-9]+"))
            zeros = Integer.parseInt(args[1]);
        else
            System.out.println("Input invalido, se setea la cantidad de ceros a 4");

        try {
            m = new CommunicationInterface(zeros);
            m.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
