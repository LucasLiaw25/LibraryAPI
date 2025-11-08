package com.liaw.dev.Library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

public class BookDTO {
    private Long id;
    private String name;
    private String title;
    private String author;
    private String isbn;
    private Boolean loan = false;
    private User user;
    private List<Loan> loans;
}
