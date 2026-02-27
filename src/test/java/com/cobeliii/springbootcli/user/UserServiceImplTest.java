package com.cobeliii.springbootcli.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    @Test
    void itShouldGetAllUsers() {
        //given
        long id = 1L;
        User user = new User("John");
        user.setId(id);

        //when
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> actual = underTest.getAllUsers();

        //Assert
        assertThat(actual).isEqualTo(List.of(new UserDto("John")));
    }

    @Test
    void itShouldGetUserById() {
        //given
        long id = 1L;
        User user = new User("John");
        user.setId(id);

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<UserDto> actual = underTest.getUserById(user.getId());

        //Assert
        assertThat(actual).isEqualTo(Optional.of(new UserDto("John")));
    }

    @Test
    void itShouldGetUserByName() {
        //given
        long id = 1L;
        User user = new User("John");
        user.setId(id);
        //when
        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));
        Optional<UserDto> actual = underTest.getUserByName(user.getName());
        //Assert
        assertThat(actual).isEqualTo(Optional.of(new UserDto("John")));
    }
}