package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.CreateUserRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public MessageResponse createUser(CreateUserRequest createUserRequest) {
        User newUser = new User();
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        newUser.setPesel(createUserRequest.getPesel());
        userRepository.save(newUser);
        return new MessageResponse("New user created successfully");
    }

    @Override
    public MessageResponse updateUser(Integer userId, CreateUserRequest createUserRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId);
        } else {
            user.get().setFirstName(createUserRequest.getFirstName());
            user.get().setLastName(createUserRequest.getLastName());
            user.get().setPesel(createUserRequest.getPesel());
            userRepository.save(user.get());
            return new MessageResponse("User updated successfully");
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        if (userRepository.getById(userId).getId() == userId) {
            userRepository.deleteById(userId);
        } else throw new ResourceNotFoundException("User", "id", userId);
    }

    @Override
    public User getASingleUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
