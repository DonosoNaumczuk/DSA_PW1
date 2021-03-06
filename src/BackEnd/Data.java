package BackEnd;

import java.io.*;

public class Data implements java.io.Serializable{
    private String operation;
    private boolean treeModified;
    private String path;
    private static int counter = 0;

    public Data (String operation, AVLTree treeState, boolean treeModified) {
        this.operation = operation;
        this.path = "src/AVLTree_data/Data"+counter+".ser";
        saveAVL(treeState);
        this.treeModified = treeModified;
        counter++;
    }

    public Data (String operation, String path, boolean treeModified) {
        this.operation = operation;
        this.path = path;
        this.treeModified = treeModified;
    }

    /**
     * Gets the tree
     *
     * @return the tree
     */
    public AVLTree getTreeState() {
        return loadAVL();
    }

    /**
     * Gets the operation
     *
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Cheks if the tree was modified
     *
     * @return true if was modified and false otherwise
     */
    public boolean wasModified() {
        return treeModified;
    }

    /**
     * Saves a AVLTree in the path of Data
     *
     * @param tree Serializable tree object to store
     */
    private void saveAVL(AVLTree tree) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tree);
            out.close();
            fileOut.close();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Loads a AVLTree from the path of data
     *
     * @return the AVLTree that was loaded
     */
    private AVLTree loadAVL() {
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

