package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public @Data
class ReturnBookRequest {
    @NotBlank
    @NotNull
    @NotEmpty
    private Integer userId;
    @NotBlank
    @NotNull
    @NotEmpty
    private Integer bookId;
}
