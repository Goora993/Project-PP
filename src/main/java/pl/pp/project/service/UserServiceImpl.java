package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.CreateUserRequest;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.dto.impl.UserBookWithAuthorDto;
import pl.pp.project.dto.mappers.UserMapper;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        User newUser = new User();
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        newUser.setPesel(createUserRequest.getPesel());
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User updateUser(Integer userId, CreateUserRequest createUserRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId);
        } else {
            user.get().setFirstName(createUserRequest.getFirstName());
            user.get().setLastName(createUserRequest.getLastName());
            user.get().setPesel(createUserRequest.getPesel());
            userRepository.save(user.get());
            return user.get();
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        if (userRepository.getById(userId).getId() == userId) {
            userRepository.deleteById(userId);
        } else throw new ResourceNotFoundException("User", "id", userId);
    }

    @Override
    public UserBookWithAuthorDto getASingleUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return UserMapper.userToUserBookWithAuthorDto(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
