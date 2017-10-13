package BackEnd;

import java.io.*;

public class Data implements java.io.Serializable{
    private String operation;
    private AVLTree treeState; //HAVE TO be Serializable!
    private boolean treeModified;
    private String path;
    private static int counter = 0;

    public Data (String operation, AVLTree treeState, boolean treeModified) {
        this.operation = operation;
        this.treeState = treeState;
        this.treeModified = treeModified;
        this.path = "src/AVLTree_data/Data"+counter+"ser"; //chequear el path
        counter++;
    }

    public AVLTree getTreeState() {
        return loadAVL();
    }

    public String getOperation() {
        return operation;
    }

    public boolean wasModified() {
        return treeModified;
    }

    public void saveAVL() {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(treeState);
            out.close();
            fileOut.close();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
    }

    public AVLTree loadAVL() {
        AVLTree avlTree = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            avlTree = (AVLTree) in.readObject();
            in.close();
            fileIn.close();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
        catch(ClassNotFoundException c) {
            System.out.println("BackEnd.AVLTree_data class not found");
            c.printStackTrace();
        }
        return avlTree;
    }
}

