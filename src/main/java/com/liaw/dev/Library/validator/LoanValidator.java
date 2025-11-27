package com.liaw.dev.Library.validator;

import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.errors.BookErrors.BookLoanException;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanMaxException;
import com.liaw.dev.Library.errors.LoanErrors.LoanNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanWrongException;
import com.liaw.dev.Library.errors.UserErrors.UserNotFoundException;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.LoanRepository;
import com.liaw.dev.Library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoanValidator {

    private final LoanRepository repository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void validateId(Long id){
        Optional<Loan> loan = repository.findById(id);

        if (loan.isEmpty()){
            throw new LoanNotFoundException("Empréstimo não encontrado.");
        }
    }

    public void validateUserRequest(String registration, String isbn){

        Optional<Book> find_book = bookRepository.findByIsbn(isbn);
        Optional<User> find_user = userRepository.findByRegistration(registration);

        if (find_user.isEmpty()){
            throw new UserNotFoundException("Usuário não encontrado");
        }

        if (find_book.isEmpty()){
            throw new BookNotFoundException("Livro não encontrado");
        }
    }

    public void validateReturn(Long id, String registration, String isbn){
        validateId(id);
        validateUserRequest(registration, isbn);
        Loan loan = repository.findById(id).get();

        if (!loan.getUser().getRegistration().equals(registration) && !loan.getBook().getIsbn().equals(isbn)){
            throw new LoanWrongException("ISBN ou Registro não pertencem a esse empréstimo");
        }
    }

    public void validateLoan(String registration, String isbn){
        validateUserRequest(registration, isbn);

        Optional<Book> find_book = bookRepository.findByIsbn(isbn);
        Optional<User> find_user = userRepository.findByRegistration(registration);

        if (find_book.isPresent()){
            Book book = find_book.get();
            if (book.getLoan() == true){
                throw new BookLoanException("Livro já emprestado. Aguarde o retorno do livro.");
            }
        }
    }



}
