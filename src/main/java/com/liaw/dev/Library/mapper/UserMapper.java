package com.liaw.dev.Library.mapper;

import com.liaw.dev.Library.dto.UserDTO;
import com.liaw.dev.Library.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRegistration(),
                user.getBooks(),
                user.getLoans()
        );
    }

    public User toEntity(UserDTO user){
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRegistration(),
                user.getBooks(),
                user.getLoans()
        );
    }

}
