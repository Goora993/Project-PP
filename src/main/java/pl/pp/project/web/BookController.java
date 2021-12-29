package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.CreateBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks () {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Book> getBookById (@PathVariable("id") Integer id) {
        Book book = bookService.getASingleBook(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addBook(@RequestBody CreateBookRequest book) {
        MessageResponse newBook = bookService.createBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateBook( @PathVariable Integer id, @RequestBody CreateBookRequest book) {
        MessageResponse updateBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updateBook, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
