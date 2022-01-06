package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.CreateAuthorRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.dto.impl.AuthorToImportDto;

import java.util.List;

@Component
public interface AuthorService {
    Author createAuthor(CreateAuthorRequest createAuthorRequest);
    Author createAuthor(AuthorToImportDto authorToImportDto);
    Author updateAuthor(Integer authorId, CreateAuthorRequest createAuthorRequest);
    void deleteAuthor(Integer authorId);
    Author getASingleAuthor(Integer authorId);
    List<Author> getAllAuthors();
}
