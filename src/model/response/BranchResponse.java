package model.response;

public class BranchResponse {
    private String id;
    private String name;
    private String address;
    private int numOfBooks;

    public BranchResponse(String id, String name, String address, int numOfBooks) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numOfBooks = numOfBooks;
    }

    @Override
    public String toString() {
        return "Branch [" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", numOfBooks=" + numOfBooks +
                " ]";
    }
}
