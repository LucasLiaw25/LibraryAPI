package com.liaw.dev.Library.service;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.enums.LoanStatus;
import com.liaw.dev.Library.mapper.LoanMapper;
import com.liaw.dev.Library.mapper.UserMapper;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.LoanRepository;
import com.liaw.dev.Library.repository.UserRepository;
import com.liaw.dev.Library.validator.LoanValidator;
import com.liaw.dev.Library.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanMapper mapper;
    private final LoanValidator validator;
    private final LoanRepository repository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public LoanDTO makeLoan(String registration, String isbn){
        validator.validateLoan(registration, isbn);
        User user = userRepository.findByRegistration(registration).get();
        Book book = bookRepository.findByIsbn(isbn).get();

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setStatus(LoanStatus.PENDING);

        repository.save(loan);

        book.setLoan(true);
        book.setUser(user);
        book.getLoans().add(loan);
        user.addBook(book);

        userRepository.save(user);
        bookRepository.save(book);


        return mapper.toDTO(loan);

    }

    @Transactional
    public void returnBook(String registration, String isbn){
        validator.validateUserRequest(registration, isbn);

        User user = userRepository.findByRegistration(registration).get();
        Book book = bookRepository.findByIsbn(isbn).get();

        book.setLoan(false);

    }

}
