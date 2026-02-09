package com.cobeliii.springbootcli.user;

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
        List<Users> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(user -> new UserDto(user.getName()))
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        return userOptional
                .map(user -> new UserDto(user.getName()));
    }

    @Override
    public Optional<UserDto> getUserByName(String name) {
        Optional<Users> userOptional = userRepository.findByName(name);
        return userOptional
                .map(user -> new UserDto(user.getName()));
    }

}
