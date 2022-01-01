package pl.pp.project.dto;

import java.sql.Date;

public interface AuthorDto {
    int getId();
    String getFirstName();
    String getLastName();
    Date getDateOfBirth();
}
