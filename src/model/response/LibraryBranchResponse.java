package model.response;

public class LibraryBranchResponse {
    private final String code;
    private final String name;
    private final String address;
    private final int numOfBooks;

    public LibraryBranchResponse(String code, String name, String address, int numOfBooks) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.numOfBooks = numOfBooks;
    }

    @Override
    public String toString() {
        return "LibraryBranch [" +
                "Code='" + code + '\'' +
                ", Name='" + name + '\'' +
                ", Address='" + address + '\'' +
                ", NumberOfBooks=" + numOfBooks +
                " ]";
    }
}
