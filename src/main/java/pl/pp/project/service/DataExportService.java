package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.payloads.request.ExportAllBooksRequest;
import pl.pp.project.data.payloads.request.ExportUserWithBorrowedBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

@Component
public interface DataExportService {
    MessageResponse exportUserWithBorrowedBooks(ExportUserWithBorrowedBooksRequest exportUserWithBorrowedBooksRequest);
    MessageResponse exportAllBooks(ExportAllBooksRequest exportAllBooksRequest);
}
