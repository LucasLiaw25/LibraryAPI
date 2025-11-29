package com.liaw.dev.Library.mapper;

import com.liaw.dev.Library.dto.BookDTOResponse;
import com.liaw.dev.Library.dto.UserDTO;
import com.liaw.dev.Library.dto.UserResponse;
import com.liaw.dev.Library.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final LoanMapper loanMapper;

    public UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRegistration(),
                user.getCpf(),
                user.getBooks(),
                user.getLoans()
        );
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistration(),
                user.getBooks().stream().map(book -> new BookDTOResponse(book.getId(), book.getTitle())).toList(),
                loanMapper.toLoanResponse(user.getLoans())
        );
    }

    public User toEntity(UserDTO user){
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRegistration(),
                user.getCpf(),
                user.getBooks(),
                user.getLoans()
        );
    }

}
