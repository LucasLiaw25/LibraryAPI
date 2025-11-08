package com.liaw.dev.Library.dto;

import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.enums.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private Long id;
    private User user;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate ;
    private LoanStatus status = LoanStatus.AVAILABLE;
}
