package pl.pp.project.data.payloads.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public @Data class CreateBookRequest {
    @NotEmpty(message = "Book name must be provided")
    private String name;
    @NotEmpty(message = "ISBN must be provided")
    private String isbn;
    @NotNull(message = "Publication date must be provided")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date publicationYear;
    @NotNull(message = "Author Id must be provided")
    private int authorId;
}
