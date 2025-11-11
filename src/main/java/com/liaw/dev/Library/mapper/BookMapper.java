package com.liaw.dev.Library.mapper;

import com.liaw.dev.Library.dto.BookDTO;
import com.liaw.dev.Library.dto.BookDTOResponse;
import com.liaw.dev.Library.dto.BookResponse;
import com.liaw.dev.Library.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final LoanMapper loanMapper;

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

    public BookDTOResponse toResponse(Book book){
        return new BookDTOResponse(
                book.getId(),
                book.getTitle()
        );
    }

    public List<BookDTOResponse> toBookResponse(List<Book> books){
        return books.stream().map(this::toResponse).toList();
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
