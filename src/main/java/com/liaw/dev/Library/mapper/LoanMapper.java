package com.liaw.dev.Library.mapper;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.dto.LoanResponse;
import com.liaw.dev.Library.entity.Loan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanMapper {

    public LoanDTO toDTO(Loan loan){
        return new LoanDTO(
                loan.getId(),
                loan.getUser(),
                loan.getPaymentStatus(),
                loan.getBook(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    public LoanResponse toLoan(Loan loan){
        return new LoanResponse(
                loan.getId(),
                loan.getStatus(),
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }
    
    public List<LoanResponse> toLoanResponse(List<Loan> loans){
        return loans.stream().map(this::toLoan).toList();
    }

    public Loan toEntity(LoanDTO loan){
        return new Loan(
                loan.getId(),
                loan.getUser(),
                loan.getBook(),
                loan.getPaymentStatus(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

}
