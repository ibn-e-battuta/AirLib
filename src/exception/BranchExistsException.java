package exception;

public class BranchExistsException extends Exception {
    public BranchExistsException(String code) {
        super("Branch with code " + code + " already exists");
    }
}
