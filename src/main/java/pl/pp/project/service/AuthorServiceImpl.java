package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.CreateAuthorRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.AuthorRepository;
import pl.pp.project.dto.impl.AuthorToImportDto;
import pl.pp.project.dto.mappers.AuthorMapper;
import pl.pp.project.exception.AuthorAlreadyExistsException;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author createAuthor(CreateAuthorRequest createAuthorRequest) {
        Optional<Author> existingAuthor = authorRepository.findByFirstNameAndLastNameAndDateOfBirth(createAuthorRequest.getFirstName(), createAuthorRequest.getLastName(), createAuthorRequest.getDateOfBirth());
        if (existingAuthor.isPresent()) {
            throw new AuthorAlreadyExistsException(createAuthorRequest.getFirstName(), createAuthorRequest.getLastName(), createAuthorRequest.getDateOfBirth());
        } else {
            Author newAuthor = new Author();
            newAuthor.setFirstName(createAuthorRequest.getFirstName());
            newAuthor.setLastName(createAuthorRequest.getLastName());
            newAuthor.setDateOfBirth(createAuthorRequest.getDateOfBirth());
            authorRepository.save(newAuthor);
            return newAuthor;
        }
    }

    @Override
    public Author createAuthor(AuthorToImportDto authorToImportDto) {
        Author newAuthor = AuthorMapper.toAuthor(authorToImportDto);
        authorRepository.save(newAuthor);
        return newAuthor;
    }

    @Override
    public Author updateAuthor(Integer authorId, CreateAuthorRequest createAuthorRequest) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new ResourceNotFoundException("Author", "id", authorId);
        } else {
            author.get().setFirstName(createAuthorRequest.getFirstName());
            author.get().setLastName(createAuthorRequest.getLastName());
            author.get().setDateOfBirth(createAuthorRequest.getDateOfBirth());
            authorRepository.save(author.get());
            return author.get();
        }
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        if (authorRepository.getById(authorId).getId() == authorId) {
            authorRepository.deleteById(authorId);
        } else throw new ResourceNotFoundException("Author", "id", authorId);
    }

    @Override
    public Author getASingleAuthor(Integer authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
