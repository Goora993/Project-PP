package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.payloads.request.ImportBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

@Component
public interface DataImportService {
    MessageResponse importBooks(ImportBooksRequest importBooksRequest);
}
