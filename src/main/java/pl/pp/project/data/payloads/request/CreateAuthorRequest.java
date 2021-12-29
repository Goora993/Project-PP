package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public @Data class CreateAuthorRequest {
    @NotBlank
    @NotNull
    @NotEmpty
    private String firstName;
    @NotBlank
    @NotNull
    @NotEmpty
    private String lastName;
    @NotBlank
    @NotNull
    @NotEmpty
    private String dateOfBirth;
}
