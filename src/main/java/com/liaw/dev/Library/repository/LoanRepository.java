package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus loanStatus);
}
