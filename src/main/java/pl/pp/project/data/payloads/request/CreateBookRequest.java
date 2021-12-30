package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public @Data class CreateBookRequest {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String isbn;
    @NotBlank
    @NotNull
    private String publicationYear;
    @NotBlank
    @NotNull
    private int authorId;
    @NotBlank
    @NotNull
    private String authorFirstName;
    @NotBlank
    @NotNull
    private String authorLastName;
    @NotBlank
    @NotNull
    private String authorDateOfBirth;
}
