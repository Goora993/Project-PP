package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pp.project.data.payloads.request.ImportBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.service.DataImportService;

import javax.validation.Valid;

@RestController
@RequestMapping("/import")
public class DataImportController {
        @Autowired
        DataImportService dataImportService;


        @PostMapping("/books")
        public ResponseEntity<MessageResponse> importBooks(@Valid @RequestBody ImportBooksRequest importBooksRequest) {
            MessageResponse importBooks = dataImportService.importBooks(importBooksRequest);
            return new ResponseEntity<>(importBooks, HttpStatus.OK);
        }
}
