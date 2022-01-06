package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.payloads.request.ReturnBookRequest;
import pl.pp.project.service.ReturnBookService;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class ReturnBookController extends RequestErrorHandlingController {
    @Autowired
    ReturnBookService returnBookService;

    @PostMapping("/return")
    public ResponseEntity<BorrowedBook> returnBook(@Valid @RequestBody ReturnBookRequest returnBook) {
        BorrowedBook returnedBook = returnBookService.returnBook(returnBook);
        return new ResponseEntity<>(returnedBook, HttpStatus.OK);
    }
}
