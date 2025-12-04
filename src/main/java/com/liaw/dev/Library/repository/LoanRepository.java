package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.enums.LoanStatus;
import com.liaw.dev.Library.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus loanStatus);
    Optional<Loan> findByTxid(String txid);
    List<Loan> findByPaymentStatus(PaymentStatus status);
}
