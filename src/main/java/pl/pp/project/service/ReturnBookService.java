package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.payloads.request.ReturnBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

@Component
public interface ReturnBookService {
    MessageResponse returnBook(ReturnBookRequest returnBookRequest);
}
