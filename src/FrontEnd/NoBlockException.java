package FrontEnd;

public class NoBlockException extends RuntimeException {
    public NoBlockException(){
        super("There aren't any Blocks in the current Blockchain.");
    }
}
