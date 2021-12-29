package pl.pp.project.exception;

public class BorrowedBookNotFoundException extends NullPointerException{
    public BorrowedBookNotFoundException(Integer value) {
        super("Cannot find book with id: " + value + " on borrowed books list");
    }
}
