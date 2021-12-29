package pl.pp.project.exception;

public class BookAlreadyBorrowedException extends NullPointerException{
    public BookAlreadyBorrowedException(Integer bookId){
        super("Book with id: " + bookId + " was already borrowed.");
    }
}
