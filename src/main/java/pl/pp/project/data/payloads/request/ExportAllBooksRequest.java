package pl.pp.project.data.payloads.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

public @Data class ExportAllBooksRequest {
    @JsonProperty(value = "isBorrowed")
    private Boolean isBorrowed;
    @NotEmpty(message = "Path to export must be provided")
    private String pathToExport;
}
