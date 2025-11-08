package com.liaw.dev.Library.service;

import com.liaw.dev.Library.dto.UserDTO;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.mapper.BookMapper;
import com.liaw.dev.Library.mapper.UserMapper;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.UserRepository;
import com.liaw.dev.Library.validator.BookValidator;
import com.liaw.dev.Library.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserValidator validator;
    private final UserRepository repository;

    public UserDTO createUser(UserDTO dto){
        User user = mapper.toEntity(dto);
        repository.save(user);
        return mapper.toDTO(user);
    }

    public List<UserDTO> listUsers(){
        List<User> users = repository.findAll();
        return users.stream().map(mapper::toDTO).toList();
    }

    public UserDTO findById(Long id){
        validator.validateId(id);
        User user = repository.findById(id).get();
        return mapper.toDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO dto){
        validator.validateId(id);
        User user = mapper.toEntity(dto);
        repository.save(user);
        return dto;
    }

    public void deleteUser(Long id){
        validator.validateId(id);
        repository.deleteById(id);
    }

}
