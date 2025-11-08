package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.dto.BookDTO;
import com.liaw.dev.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library/book")
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> listBooks(){
        return ResponseEntity.ok(service.listBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO dto){
        return ResponseEntity.ok(service.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.NO_CONTENT.value(),
                        "Livro com id" + id + " deletado."
                )
        );
    }

}
