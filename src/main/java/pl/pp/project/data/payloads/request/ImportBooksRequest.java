package pl.pp.project.data.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

public @Data class ImportBooksRequest {
    @NotEmpty(message = "Path must be provided")
    private String pathToImport;
}
