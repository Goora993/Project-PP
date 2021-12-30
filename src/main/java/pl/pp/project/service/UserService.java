package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.CreateUserRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

import java.util.List;

@Component
public interface UserService {
    MessageResponse createUser(CreateUserRequest createUserRequest);
    MessageResponse updateUser(Integer authorId, CreateUserRequest createUserRequest);
    void deleteUser(Integer userId);
    User getASingleUser(Integer userId);
    List<User> getAllUsers();
}