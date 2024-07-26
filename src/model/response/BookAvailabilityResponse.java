package model.response;

public class BookAvailabilityResponse {

    private final String title;
    private final int totalCopies;
    private final int availableCopies;
    private final int borrowedCopies;

    public BookAvailabilityResponse(String title, int totalCopies, int availableCopies, int borrowedCopies) {
        this.title = title;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.borrowedCopies = borrowedCopies;
    }

    @Override
    public String toString() {
        return "[ " +
                "Title='" + title + '\'' +
                ", TotalCopies='" + totalCopies + '\'' +
                ", AvailableCopies='" + availableCopies + '\'' +
                ", BorrowedCopies='" + borrowedCopies + '\'' +
                " ]";
    }
}
