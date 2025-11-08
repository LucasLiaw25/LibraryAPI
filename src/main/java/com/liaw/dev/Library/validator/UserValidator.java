package com.liaw.dev.Library.validator;

import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validateId(Long id){
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()){
            throw new RuntimeException("Usuário com id:" + id + " não encontrado.");
        }
    }

}
