package response;

import java.util.List;

public class BookCopyResponse {
    private final String isbn;
    private final String title;
    private final String authors;
    private final String branchName;
    private final boolean isAvailable;

    public BookCopyResponse(String isbn, String title, List<String> authors, String branchName, boolean isAvailable) {
        this.isbn = isbn;
        this.title = title;
        this.authors = String.join(", ", authors);
        this.branchName = branchName;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "BookCopy [ " +
                "ISBN='" + isbn + '\'' +
                "' Title='" + title + '\'' +
                ", Author(s)='" + authors + '\'' +
                ", Branch='" + branchName + '\'' +
                ", Available='" + isAvailable + '\'' +
                " ]";
    }
}