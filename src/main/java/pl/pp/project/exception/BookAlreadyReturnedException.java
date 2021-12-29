package pl.pp.project.exception;

public class BookAlreadyReturnedException extends NullPointerException{
    public BookAlreadyReturnedException(Integer bookId){
        super("Book with id: " + bookId + " was already returned");
    }
}
