package com.anurag.smartbank.Service;

import com.anurag.smartbank.dto.request.CreateUserRequest;
import com.anurag.smartbank.dto.response.UserResponse;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.exception.DuplicateResourceException;
import com.anurag.smartbank.mapper.UserMapper;
import com.anurag.smartbank.repository.UserRepository;
import com.anurag.smartbank.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
{
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;


    @Test
    void testCreateUser()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName("Anurag Singh");
        request.setEmail("anurag@gmail.com");
        request.setPhone("9876543210");
        request.setPassword("Password123");

        User user = new User();
        user.setName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setFullName(request.getFullName());
        response.setEmail(request.getEmail());
        response.setPhone(request.getPhone());

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByPhone(request.getPhone())).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.registerUser(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(request.getFullName(), result.getFullName());
        Assertions.assertEquals(request.getEmail(), result.getEmail());
        Assertions.assertEquals(request.getPhone(), result.getPhone());

        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).existsByPhone(request.getPhone());
        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testDuplicateEmail()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName("Anurag Singh");
        request.setEmail("asdf@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("123456789");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        Assertions.assertThrows(DuplicateResourceException.class,
                () -> userService.registerUser(request));
        verify(userRepository, never()).save(any(User.class));
        verify(userRepository).existsByEmail(request.getEmail());

    }

    @Test
    void testDuplicationPhone()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName("Anurag Singh");
        request.setEmail("asdf@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("123456789");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByPhone(request.getPhone())).thenReturn(true);
        Assertions.assertThrows(DuplicateResourceException.class,
                () -> userService.registerUser(request));
        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).existsByEmail(request.getEmail());
    }

    @Test
    void testPasswordIsEncryptedBeforeSaving()
    {
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName("Anurag Singh");
        request.setEmail("asdf@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("123456789");

        User user = new User();
        user.setName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setFullName(request.getFullName());
        response.setEmail(request.getEmail());
        response.setPhone(request.getPhone());

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByPhone(request.getPhone())).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("EncryptedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        userService.registerUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertEquals("EncryptedPassword",savedUser.getPassword());

    }
}
