public class AVLTree {
    private Node root;

    private static class Node {
        int height;
        int value;
        Node left;
        Node right;

        private Node(int value) {
            this.value = value;
            height = 1;
        }

        private Node getLeft(){
            return left;
        }

        private Node getRight(){
            return right;
        }

        private void setLeft(Node left){
            this.left = left;
            int heightl = (left==null)?0:left.height;
            int heightr = (right==null)?0:right.height;
            height = (heightl>heightr)?heightl:heightr;
            height++;
        }

        private void setRight(Node right){
            this.right = right;
            int heightl = (left==null)?0:left.height;
            int heightr = (right==null)?0:right.height;
            height = (heightl>heightr)?heightl:heightr;
            height++;
        }

        private int getBalanceFactor(){
            int heightl = (left==null)?0:left.height;
            int heightr = (right==null)?0:right.height;
            return heightr-heightl;
        }
    }

    public boolean add(int number) {
        Node aux = add(root, number);
        if(aux != null) {
            root = rotate(aux);
            return true;
        }
        return false;

    }

    /* Right-Right case */
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.setRight(newRoot.left);
        newRoot.setLeft(root);
        return newRoot;
    }

    /* Left-Left case */
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.setLeft(newRoot.right);
        newRoot.setRight(root);
        return newRoot;
    }

    /* Left-Right case */
    private Node rotateLeftRight(Node root) {
        root.setLeft(rotateLeft(root.left));
        return rotateRight(root);
    }

    private Node rotateRightLeft(Node root) {
        root.setRight(rotateRight(root.right));
        return rotateLeft(root);
    }

    //Faltarian hacer los rotate
    private Node add(Node current, int number) {
        if(current == null)
            return new Node(number);
        if(number == current.value)
            return null;

        Node aux;
        if(number > current.value) {
            aux = add(current.right, number);
            if(aux == null)
                return null;
            current.setRight(aux);
        } else {
            aux = add(current.left, number);
            if(aux == null)
                return null;
            current.setLeft(aux);
        }
        return rotate(current);
    }

    private Node rotate(Node current){
        int bf = current.getBalanceFactor();
        if (bf < -1 && current.value < current.left.value)
            return rotateRight(current);

        // Right Right Case
        if (bf > 1 && current.value < current.right.value)
            return rotateLeft(current);

        // Left Right Case
        if (bf < -1 && current.value > current.left.value) {
            return rotateLeftRight(current);
        }

        // Right Left Case
        if (bf > 1 && current.value < current.right.value) {
            return rotateRightLeft(current);
        }
        return current;
    }

    //nada de abajo esta testeado
     public void print() {
        if(root!=null) {
            System.out.println("\\- " + root.value);
            printTree(root,"   ");
        }
        //se puede agregar algo para cuando es null
    }

    private void print(Node n, String prev, boolean last) {
        if(n!=null) {
            if(last) {
                prev=prev.concat("\\- ");
            }
            else {
                prev=prev.concat("|- ");
            }
            System.out.println(prev+n.value);
        }
    }
    
    private void printTree(Node n, String prev){
        boolean right=n.right==null;
        boolean left=n.left==null;
        if(!right){
            if(!left) {
                print(n.right, prev, false);          //puede cambiar
                printTree(n.right, prev+"|  ");
                print(n.left, prev, true);
                printTree(n.left,prev+"   ");
            }else{
                print(n.right, prev, true);
                printTree(n.right,prev+"   ");
            }
        }else{
            if(!left){
                print(n.left, prev, true);
                printTree(n.left,prev+"   ");
            }
        }
    }
}
