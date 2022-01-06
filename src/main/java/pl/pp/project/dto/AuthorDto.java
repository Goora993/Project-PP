package pl.pp.project.dto;

import java.sql.Date;

public interface AuthorDto {
    String getFirstName();
    String getLastName();
    Date getDateOfBirth();
}
