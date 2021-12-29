package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pp.project.data.payloads.request.BorrowBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.service.BorrowBookService;


@RestController
@RequestMapping("/book")
public class BorrowBookController {
    @Autowired
    BorrowBookService borrowBookService;

    @PostMapping("/borrow")
    public ResponseEntity<MessageResponse> borrowBook(@RequestBody BorrowBookRequest borrowBook) {
        MessageResponse borrowedBook = borrowBookService.borrowBook(borrowBook);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }
}
