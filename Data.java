package BackEnd;
import BackEnd.AVLTree;

public class Data {
    private String operation;
    private AVLTree treeState; //May be Serializable?

    public Data () {
        this.operation = null;
        this.treeState = null;
    }

    public Data (String operation, AVLTree treeState) {
        this.operation = operation;
        this.treeState = treeState;
    }

    public AVLTree getTreeState(){
        return treeState;
    }

    public String getOperation(){
        return operation;
    }
}
