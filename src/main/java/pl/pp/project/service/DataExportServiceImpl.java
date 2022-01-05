package pl.pp.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.ExportAllBooksRequest;
import pl.pp.project.data.payloads.request.ExportUserWithBorrowedBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.dto.BookDto;
import pl.pp.project.dto.impl.UserBookWithAuthorDto;
import pl.pp.project.dto.mappers.BookMapper;
import pl.pp.project.dto.mappers.UserMapper;
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
        UserBookWithAuthorDto userBookWithAuthorDto = UserMapper.userToUserBookWithAuthorDto(user);
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(exportUserWithBorrowedBooksRequest.getPathToExport() + "/user.json"), userBookWithAuthorDto);
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
        List<BookDto> books;
        if(isBorrowed == Boolean.TRUE){
            books = BookMapper.bookListToBookWithAuthorDtoList(bookRepository.findBooksByIsBorrowed(isBorrowed));
            pathToExport += "/borrowedBooks.json";
            message = "Borrowed books list exported successfully";
        } else if(isBorrowed == Boolean.FALSE) {
            books = BookMapper.bookListToBookWithAuthorDtoList(bookRepository.findBooksByIsBorrowed(isBorrowed));
            pathToExport += "/notBorrowedBooks.json";
            message = "Not borrowed books list exported successfully";
        } else {
            books = BookMapper.bookListToBookWithAuthorDtoList(bookRepository.findAll());
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
