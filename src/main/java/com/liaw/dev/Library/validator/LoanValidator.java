package com.liaw.dev.Library.validator;

import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoanValidator {

    private final LoanRepository repository;

    public void validateId(Long id){
        Optional<Loan> loan = repository.findById(id);

        if (loan.isEmpty()){
            throw new RuntimeException("Empréstimo com id:" + id + " não encontrado.");
        }
    }

}
