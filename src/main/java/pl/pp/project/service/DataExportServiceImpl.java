package pl.pp.project.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.ExportAllBooksRequest;
import pl.pp.project.data.payloads.request.ExportUserWithBorrowedBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.exception.ResourceNotFoundException;

import java.io.*;
import java.util.List;

@Service
public class DataExportServiceImpl implements DataExportService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public MessageResponse exportUserWithBorrowedBooks(ExportUserWithBorrowedBooksRequest exportUserWithBorrowedBooksRequest) {
        Integer userId = exportUserWithBorrowedBooksRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(exportUserWithBorrowedBooksRequest.getPathToExport() + "/user.json"), user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new MessageResponse("User with borrowed books data exported successfully");
    }

    @Override
    public MessageResponse exportAllBooks(ExportAllBooksRequest exportAllBooksRequest) {
        Boolean isBorrowed = exportAllBooksRequest.getIsBorrowed();
        String pathToExport = exportAllBooksRequest.getPathToExport();
        String message;
        List<Book> books;
        if(isBorrowed == Boolean.TRUE){
            books = bookRepository.findBooksByIsBorrowed(isBorrowed);
            pathToExport += "/borrowedBooks.json";
            message = "Borrowed books list exported successfully";
        } else if(isBorrowed == Boolean.FALSE) {
            books = bookRepository.findBooksByIsBorrowed(isBorrowed);
            pathToExport += "/notBorrowedBooks.json";
            message = "Not borrowed books list exported successfully";
        } else {
            books = bookRepository.findAll();
            pathToExport += "/allBooks.json";
            message = "All books list exported successfully";
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathToExport), books);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MessageResponse(message);
    }
}
