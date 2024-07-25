package exception;

public class MaxRenewalsException extends Exception {
    public MaxRenewalsException(int maxRenewals) {
        super("Maximum number of renewals " + maxRenewals + " reached");
    }
}
