package pl.pp.project.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.CreateAuthorRequest;
import pl.pp.project.service.AuthorServiceImpl;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl authorService;

    private final CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest();
    private final Author author = new Author("Arnold", "Boczek", Date.valueOf("1970-10-10"));

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(authorService.createAuthor(any(CreateAuthorRequest.class))).thenReturn(author);
        createAuthorRequest.setFirstName("Arnold");
        createAuthorRequest.setLastName("Boczek");
        createAuthorRequest.setDateOfBirth(Date.valueOf("1970-10-10"));

        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyy")
                .create();

        mockMvc.perform(post("/author/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createAuthorRequest)))
                .andExpect(status().isCreated());
    }

}
