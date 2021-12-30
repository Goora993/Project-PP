package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

public @Data class CreateUserRequest {
    @NotEmpty(message = "User first name must be provided")
    private String firstName;
    @NotEmpty(message = "User last name must be provided")
    private String lastName;
    @NotEmpty(message = "User pesel must be provided")
    private String pesel;
}
