package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.payloads.request.BorrowBookRequest;

@Component
public interface BorrowBookService {
    BorrowedBook borrowBook(BorrowBookRequest borrowBookRequest);
}
