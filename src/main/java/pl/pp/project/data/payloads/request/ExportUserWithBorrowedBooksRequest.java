package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public @Data class ExportUserWithBorrowedBooksRequest {
    @NotNull(message = "User Id must be provided")
    private int userId;
    @NotEmpty(message = "Path must be provided")
    private String pathToExport;
}
