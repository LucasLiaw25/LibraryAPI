package com.liaw.dev.Library.service;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.enums.LoanStatus;
import com.liaw.dev.Library.enums.PaymentStatus;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanMaxException;
import com.liaw.dev.Library.errors.UserErrors.UserNotFoundException;
import com.liaw.dev.Library.mapper.LoanMapper;
import com.liaw.dev.Library.mapper.UserMapper;
import com.liaw.dev.Library.pix.EfiPixService;
import com.liaw.dev.Library.pix.PixResponse;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.LoanRepository;
import com.liaw.dev.Library.repository.UserRepository;
import com.liaw.dev.Library.validator.LoanValidator;
import com.liaw.dev.Library.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanMapper mapper;
    private final LoanValidator validator;
    private final LoanRepository repository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final EfiPixService pixService;
    private final BigDecimal FINE_PER_DAY = new BigDecimal("2.00");

    @Transactional
    public PixResponse makeLoan(String registration, String isbn){
        validator.validateLoan(registration, isbn);
        User user = userRepository.findByRegistration(registration)
                .orElseThrow(()->new UserNotFoundException("Usuário não encontrado"));
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(()-> new BookNotFoundException("Livro não encontrado"));

        if(user.getBooks().size() == 3){
            throw new LoanMaxException("Limite de empréstimos atingido.");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setStatus(LoanStatus.PENDING);
        loan.setPaymentStatus(PaymentStatus.PENDING);
        repository.save(loan);

        JSONObject pixResponseJson = pixService.createPixCode(book.getLoanPrice(), user.getName());
        PixResponse pixResponse = mapPixResponse(pixResponseJson, loan.getId(), book.getLoanPrice());

        book.setLoan(true);
        book.setUser(user);
        book.getLoans().add(loan);
        user.addBook(book);

        userRepository.save(user);
        bookRepository.save(book);

        return pixResponse;
    }

    private PixResponse mapPixResponse(JSONObject pixResponseJson, Long loanId, BigDecimal value){
        String txid = pixResponseJson.getString("txid");
        String pixCopieECola = pixResponseJson.getString("pixCopiaECola");

        return PixResponse.builder()
                .loanId(loanId)
                .txid(txid)
                .pixCopiaECola(pixCopieECola)
                .value(value)
                .build();

    }

    public List<LoanDTO> listLoan(){
        List<Loan> loans = repository.findAll();
        return loans.stream().map(mapper::toDTO).toList();
    }

    public List<LoanDTO> listPendingLoan(){
        List<Loan> loans = repository.findByStatus(LoanStatus.PENDING);
        return loans.stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public LoanDTO returnBook(Long id, String registration, String isbn){
        validator.validateReturn(id, registration, isbn);
        Loan loan = repository.findById(id).get();

        User user = loan.getUser();
        Book book = loan.getBook();

        loan.setUserReturnDate(LocalDate.now());

        if (loan.getUserReturnDate().isAfter(loan.getReturnDate())){
            long daysDelay = ChronoUnit.DAYS.between(loan.getReturnDate(), loan.getUserReturnDate());
            BigDecimal calculateFine = FINE_PER_DAY.multiply(new BigDecimal(daysDelay));
            loan.setFineAmount(calculateFine);
        }else{
            loan.setFineAmount(BigDecimal.ZERO);
        }

        book.setLoan(false);
        book.setUser(null);
        book.getLoans().remove(loan);

        user.getBooks().remove(book);
        loan.setStatus(LoanStatus.RETURNED);

        repository.save(loan);
        userRepository.save(user);
        bookRepository.save(book);

        return mapper.toDTO(loan);
    }


}
