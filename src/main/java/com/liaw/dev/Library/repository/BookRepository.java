package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
