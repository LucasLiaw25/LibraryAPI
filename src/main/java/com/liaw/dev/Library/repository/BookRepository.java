package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.loans WHERE b.id = :id")
    Optional<Book> findByIdWithLoans(@Param("id") Long id);
}
