package BackEnd;

public class Data {
    private String operation;
    private AVLTree treeState; //HAVE TO be Serializable!
    private boolean treeModified;

    public Data (String operation, AVLTree treeState, boolean treeModified) {
        this.operation = operation;
        this.treeState = treeState;
        this.treeModified = treeModified;
    }

    public AVLTree getTreeState(){
        return treeState;
    }

    public String getOperation(){
        return operation;
    }

    public boolean wasModified() {
        return treeModified;
    }
}

