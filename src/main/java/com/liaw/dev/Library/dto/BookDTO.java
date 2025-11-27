package com.liaw.dev.Library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Boolean loan = false;
    private BigDecimal loanPrice;
    private User user;
    private List<Loan> loans;
}
