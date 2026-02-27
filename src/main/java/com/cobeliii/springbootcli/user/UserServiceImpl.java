package com.cobeliii.springbootcli.user;

import com.cobeliii.springbootcli.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(user -> new UserDto(user.getName()))
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(user -> new UserDto(user.getName()))
                .orElseThrow(() -> new UserNotFoundException("User not Found")));
    }

    @Override
    public Optional<UserDto> getUserByName(String name) {
        return Optional.ofNullable(userRepository.findByName(name)
                .map(user -> new UserDto(user.getName()))
                .orElseThrow(() -> new UserNotFoundException("User not Found")));
    }

}
