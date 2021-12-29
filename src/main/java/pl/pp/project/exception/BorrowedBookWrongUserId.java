package pl.pp.project.exception;

public class BorrowedBookWrongUserId extends NullPointerException {
    public BorrowedBookWrongUserId(Integer userId, Integer bookId){
        super("User with id: " + userId + " doesn't have book with id: " + bookId + " borrowed");
    }
}
