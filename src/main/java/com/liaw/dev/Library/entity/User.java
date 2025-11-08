package com.liaw.dev.Library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer registration;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Book> books;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Loan> loans;

    public User(Long id, String name, String email, String password, Integer registration, List<Book> books, List<Loan> loans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.registration = registration;
        this.books = books;
        this.loans = loans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
