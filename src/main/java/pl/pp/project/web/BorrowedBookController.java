package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pp.project.dto.BookDto;
import pl.pp.project.service.BorrowedBookService;

import java.util.List;

@RestController
@RequestMapping("/borrowed")
public class BorrowedBookController {

    @Autowired
    BorrowedBookService borrowedBookService;

    @GetMapping("/find/{userId}")
    public ResponseEntity<List<BookDto>> getBorrowedBooksByUserId(@PathVariable Integer userId) {
        List<BookDto> books = borrowedBookService.findBooksByUserId(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
