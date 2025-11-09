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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final UserValidator validator;
    private final UserRepository repository;

    public UserDTO createUser(UserDTO dto){
        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRegistration(generateRegistration());
        repository.save(user);
        return mapper.toDTO(user);
    }

    private String generateRegistration(){
        Random random = new Random();
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);

        String registration = String.valueOf(text.charAt(random.nextInt(10)))
                + String.valueOf(text.charAt(random.nextInt(10)))
                + String.valueOf(text.charAt(random.nextInt(10)))
                + num1 + num2 + num3;

        return registration;

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
