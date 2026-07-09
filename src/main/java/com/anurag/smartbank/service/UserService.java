package com.anurag.smartbank.service;


import com.anurag.smartbank.dto.request.CreateUserRequest;
import com.anurag.smartbank.dto.response.UserResponse;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.exception.DuplicateResourceException;
import com.anurag.smartbank.mapper.UserMapper;
import com.anurag.smartbank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder)
    {
        this.userRepository=userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public UserResponse registerUser(CreateUserRequest request)
    {
        if (userRepository.existsByEmail(request.getEmail()))
        {
            throw new DuplicateResourceException("Email already exists.");
        }
        if (userRepository.existsByPhone(request.getPhone()))
        {
            throw new DuplicateResourceException("Phone number already registered");
        }
        User user = userMapper.toEntity(request);
        String rawPassword = user.getPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encryptedPassword);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
