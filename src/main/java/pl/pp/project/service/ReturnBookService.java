package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.payloads.request.ReturnBookRequest;

@Component
public interface ReturnBookService {
    BorrowedBook returnBook(ReturnBookRequest returnBookRequest);
}
