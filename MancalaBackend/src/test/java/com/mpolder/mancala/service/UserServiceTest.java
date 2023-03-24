package com.mpolder.mancala.service;

import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void assertRegisterIfMissingRegistersUser() {
        User user = new User("t1", "t2");
        when(mockUserRepository.existsById(user.getEmail())).thenReturn(false);
        when(mockUserRepository.save(user)).thenReturn(user);

        User actual = userService.registerIfMissing(user);

        verify(mockUserRepository, times(1)).existsById(user.getEmail());
        verify(mockUserRepository, times(1)).save(user);
        assertEquals(user, actual);
    }

    @Test
    public void assertRegisterIfMissingSkipsRegisterUser() {
        User user = new User("t1", "t2");
        when(mockUserRepository.existsById(user.getEmail())).thenReturn(true);

        User actual = userService.registerIfMissing(user);

        verify(mockUserRepository, times(1)).existsById(user.getEmail());
        verify(mockUserRepository, times(0)).save(user);
        assertEquals(user, actual);
    }

    @Test
    public void assertGetByEmailAccessesRepository() {
        User user = new User("t1", "t1");
        when(mockUserRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        User actual = userService.getByEmail(user.getEmail());

        verify(mockUserRepository, times(1)).findById(user.getEmail());
        assertEquals(user, actual);
    }

    @Test
    public void assertGetByInvalidEmailThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail("random"), "User not found");
    }
}
