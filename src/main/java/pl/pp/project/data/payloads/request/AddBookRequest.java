package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public @Data class AddBookRequest {
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


}
