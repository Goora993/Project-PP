package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.CreateAuthorRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

import java.util.List;

@Component
public interface AuthorService {
    MessageResponse createAuthor(CreateAuthorRequest createAuthorRequest);
    MessageResponse updateAuthor(Integer authorId, CreateAuthorRequest createAuthorRequest);
    void deleteAuthor(Integer authorId);
    Author getASingleAuthor(Integer authorId);
    List<Author> getAllAuthors();
}
