package exception;

public class BranchNotFoundException extends Exception {
    public BranchNotFoundException(String branchId) {
        super("Branch with id: " + branchId + " not found.");
    }
}
