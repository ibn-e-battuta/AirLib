package exception;

public class NonEmptyBranchException extends Exception {
    public NonEmptyBranchException(String branchId, int numOfBooks) {
        super("Cannot remove branch " + branchId + ". It still contains " + numOfBooks + " books.");
    }
}
