package com.anurag.smartbank.mapper;

import com.anurag.smartbank.dto.request.CreateUserRequest;
import com.anurag.smartbank.dto.response.UserResponse;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.repository.UserRepository;
import org.springframework.stereotype.Component;


@Component
public class UserMapper
{
    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User toEntity(CreateUserRequest request)
    {
        User user = new User();

        user.setName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        return user;
    }
    public UserResponse toResponse(User user)
    {
        UserResponse userResponse = new  UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getName());
        userResponse.setPhone(user.getPhone());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;
    }
}
