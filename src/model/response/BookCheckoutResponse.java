package model.response;

public class BookCheckoutResponse {
    private final String bookName;
    private final String checkOutDate;
    private final String returnDate;

    public BookCheckoutResponse(String bookName, String checkOutDate, String returnDate) {
        this.bookName = bookName;
        this.checkOutDate = checkOutDate;
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BookCheckout [ " +
                "BookName='" + bookName + '\'' +
                ", CheckOutDate='" + checkOutDate + '\'' +
                ", ReturnDate='" + returnDate + '\'' +
                " ]";

    }
}
