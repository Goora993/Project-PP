package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pp.project.data.payloads.request.ExportAllBooksRequest;
import pl.pp.project.data.payloads.request.ExportUserWithBorrowedBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.service.DataExportService;

import javax.validation.Valid;

@RestController
@RequestMapping("/export")
public class DataExportController extends RequestErrorHandlingController{
    @Autowired
    DataExportService dataExportService;

    @PostMapping("/user")
    public ResponseEntity<MessageResponse> exportUserWithBorrowedBooks(@Valid @RequestBody ExportUserWithBorrowedBooksRequest exportUserWithBorrowedBooksRequest) {
        MessageResponse export = dataExportService.exportUserWithBorrowedBooks(exportUserWithBorrowedBooksRequest);
        return new ResponseEntity<>(export, HttpStatus.OK);
    }

    @PostMapping("/book/all")
    public ResponseEntity<MessageResponse> exportAllBooks(@Valid @RequestBody ExportAllBooksRequest exportAllBooksRequest) {
        MessageResponse export = dataExportService.exportAllBooks(exportAllBooksRequest);
        return new ResponseEntity<>(export, HttpStatus.OK);
    }
}
