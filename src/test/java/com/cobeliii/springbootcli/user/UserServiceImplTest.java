package com.cobeliii.springbootcli.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserServiceImpl.class)
@Testcontainers
class UserServiceImplTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void itShouldGetAllUsers() {
        //given
        User user = new User("John");
        //when
        userRepository.save(user);

        List<UserDto> allUsers = underTest.getAllUsers();
        assertThat(allUsers).containsOnly(new UserDto("John"));
    }

    @Test
    void itShouldGetUserById() {
        //given
        User user = new User("John");
        //when
        userRepository.save(user);
        Optional<UserDto> actual = underTest.getUserById(user.getId());
        //Assert
        assertThat(actual).isPresent().get().isEqualTo(new UserDto("John"));
    }
}