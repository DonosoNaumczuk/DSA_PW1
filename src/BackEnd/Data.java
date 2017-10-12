package BackEnd;

public class Data {
    private String operation;
    private AVLTree treeState; //May be Serializable?
    private boolean treeModified;

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

    public boolean getTreeModified() {
        return treeModified;
    }
}

