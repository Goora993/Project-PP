package pl.pp.project.exception;

import javax.persistence.EntityExistsException;
import java.sql.Date;

public class AuthorAlreadyExistsException extends EntityExistsException {
    public AuthorAlreadyExistsException(String firstName, String lastName, Date dateOfBirth) {
        super("Author " + firstName + " " + lastName + " born in " + dateOfBirth + " already exist");
    }
}
