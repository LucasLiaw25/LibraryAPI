package com.liaw.dev.Library.mapper;

import com.liaw.dev.Library.dto.BookDTO;
import com.liaw.dev.Library.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book){
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getLoan(),
                book.getUser(),
                book.getLoans()
        );
    }

    public Book toEntity(BookDTO book){
        return new Book(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getLoan(),
                book.getUser(),
                book.getLoans()
        );
    }

}
