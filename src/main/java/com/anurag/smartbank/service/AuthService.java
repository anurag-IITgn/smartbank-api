package com.anurag.smartbank.service;

import com.anurag.smartbank.dto.request.LoginRequest;
import com.anurag.smartbank.dto.response.LoginResponse;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.exception.InvalidCredentialsException;
import com.anurag.smartbank.exception.UserNotFoundException;
import com.anurag.smartbank.repository.UserRepository;
import com.anurag.smartbank.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public LoginResponse login(LoginRequest request)
    {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new UserNotFoundException("User not found with this emailId. Try again"));
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword()))
        {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String token = jwtService.generateToken((user.getEmail()));
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setToken(token);

        return response;

    }
}
