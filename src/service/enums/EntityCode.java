package service.enums;

public enum EntityCode {
    BOOK_COPY("BI"),
    BOOK_CHECKOUT("C"),
    BOOK_RESERVATION("BR"),
    PATRON("P");

    private final String code;

    EntityCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}