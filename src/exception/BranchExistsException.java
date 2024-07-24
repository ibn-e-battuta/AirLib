package exception;

public class BranchExistsException extends Exception {
    public BranchExistsException(String name, String address) {
        super("Branch with name " + name + " and address " + address + " already exists");
    }
}
