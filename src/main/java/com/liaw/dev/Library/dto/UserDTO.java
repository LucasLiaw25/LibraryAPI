package com.liaw.dev.Library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.Loan;
import jakarta.persistence.OneToMany;

import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer registration;
    List<Book> books;
    private List<Loan> loans;
}
