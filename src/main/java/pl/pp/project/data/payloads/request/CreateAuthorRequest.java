package pl.pp.project.data.payloads.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public @Data class CreateAuthorRequest {
    @NotEmpty(message = "Author first name must be provided")
    private String firstName;
    @NotEmpty(message = "Author last name must be provided")
    private String lastName;
    @NotNull(message = "Author date of birth be provided")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfBirth;
}
