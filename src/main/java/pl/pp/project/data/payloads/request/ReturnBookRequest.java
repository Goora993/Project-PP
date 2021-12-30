package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

public @Data
class ReturnBookRequest {
    @NotNull(message = "User Id must be provided")
    private Integer userId;
    @NotNull(message = "Book Id must be provided")
    private Integer bookId;
}
