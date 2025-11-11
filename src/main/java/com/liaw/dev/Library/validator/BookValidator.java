package com.liaw.dev.Library.validator;

import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository repository;

    public void validateId(Long id){
        Optional<Book> book = repository.findById(id);

        if (book.isEmpty()){
            throw new BookNotFoundException("Livro não encontrado.");
        }
    }

}
