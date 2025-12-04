package com.liaw.dev.Library.entity;

import com.liaw.dev.Library.enums.LoanStatus;
import com.liaw.dev.Library.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_loan")
@EntityListeners(AuditingEntityListener.class)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String txid;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @CreatedDate
    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate userReturnDate;
    private BigDecimal fineAmount;

    @PrePersist
    protected void prePersist(){
        if (this.loanDate == null){
            this.loanDate = LocalDate.now();
        }
        if (this.returnDate == null){
            this.returnDate = loanDate.plusWeeks(1);
        }
    }

    public Loan(Long id,  User user, Book book, PaymentStatus paymentStatus, LocalDate loanDate, LocalDate returnDate, LoanStatus status) {
        this.id = id;
        this.user = user;
        this.paymentStatus = paymentStatus;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }




}
