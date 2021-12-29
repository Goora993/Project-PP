package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.payloads.request.BorrowBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

@Component
public interface BorrowBookService {
    MessageResponse borrowBook(BorrowBookRequest borrowBookRequest);
}
