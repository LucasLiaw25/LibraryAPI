package com.liaw.dev.Library.service;

import com.liaw.dev.Library.dto.BookDTO;
import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.mapper.BookMapper;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper mapper;
    private final BookValidator validator;
    private final BookRepository repository;

    public BookDTO createBook(BookDTO dto){
        Book book = mapper.toEntity(dto);
        repository.save(book);
        return mapper.toDTO(book);
    }

    public List<BookDTO> listBooks(){
        List<Book> books = repository.findAll();
        return books.stream().map(mapper::toDTO).toList();
    }

    public BookDTO findById(Long id){
        validator.validateId(id);
        Book book = repository.findById(id).get();
        return mapper.toDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO dto){
        validator.validateId(id);
        Book book = mapper.toEntity(dto);
        book.setId(id);
        repository.save(book);
        return mapper.toDTO(book);
    }

    public void deleteBook(Long id){
        validator.validateId(id);
        repository.deleteById(id);
    }

}
