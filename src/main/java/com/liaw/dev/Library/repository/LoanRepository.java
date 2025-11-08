package com.liaw.dev.Library.repository;

import com.liaw.dev.Library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
