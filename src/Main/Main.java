package Main;

import FrontEnd.CommunicationInterface;

public class Main {
    private static final int ZEROS = 4;

    public static void main(String[] args) {
        CommunicationInterface commInterface = null;
        boolean loaded = false;

        if (args.length == 2 && args[0].matches("zeros")) {
            if (args[1].matches("[0-9]+")) {
                int zeros = Integer.parseInt(args[1]);
                System.out.println("Setting up a new blockchain with zero quantity set to " + zeros);
                loaded = true;
                try {
                    commInterface = new CommunicationInterface(zeros);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("Invalid zero parameter, must be a positive integer");
        }
        else if (args.length == 1 && args[0].matches("load")) {
            /** We instance this with ZEROS.
             *  That don't even matter because we will load
             *  a blockchain on it.
             */
            try {
                commInterface = new CommunicationInterface(ZEROS);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (commInterface != null && commInterface.loadBlockchain()) {
                loaded = true;
                System.out.println("Blockchain loaded succesfully");
                System.out.println("The blockchain loaded has zeros set to " + commInterface.getBlockChain().getZeros());
            }
            else
                System.out.println("No blockchain to load");
        }
        else if (args.length == 0) {
            System.out.println("No arguments received");
        }

        if(!loaded) {
            System.out.println("Setting up a new blockchain with zero quantity set to " + ZEROS);
            try {
                commInterface = new CommunicationInterface(ZEROS);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            commInterface.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
