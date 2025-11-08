package com.liaw.dev.Library.mapper;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDTO toDTO(Loan loan){
        return new LoanDTO(
                loan.getId(),
                loan.getUser(),
                loan.getBook(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    public Loan toEntity(LoanDTO loan){
        return new Loan(
                loan.getId(),
                loan.getUser(),
                loan.getBook(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

}
