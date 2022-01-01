package pl.pp.project.dto;

import java.sql.Date;

public interface BookDto {
    int getId();
    String getName();
    String getIsbn();
    Date getPublicationYear();
    boolean isBorrowed();
    AuthorDto getAuthor();
}
