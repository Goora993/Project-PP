package pl.pp.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.CreateAuthorRequest;
import pl.pp.project.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController extends RequestErrorHandlingController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id) {
        Author author = authorService.getASingleAuthor(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(@Valid @RequestBody CreateAuthorRequest author) {
        Author newAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody CreateAuthorRequest author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Integer id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}