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

}
