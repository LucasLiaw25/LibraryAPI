package com.liaw.dev.Library.dto;


import com.liaw.dev.Library.enums.LoanStatus;

import java.time.LocalDate;

public record LoanResponse(Long id, LoanStatus status, LocalDate loanDate, LocalDate returnDate) {
}

