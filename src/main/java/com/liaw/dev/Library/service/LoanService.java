package com.liaw.dev.Library.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.entity.Book;
import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.enums.LoanStatus;
import com.liaw.dev.Library.enums.PaymentStatus;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanMaxException;
import com.liaw.dev.Library.errors.LoanErrors.LoanNotFoundException;
import com.liaw.dev.Library.errors.UserErrors.UserNotFoundException;
import com.liaw.dev.Library.mapper.LoanMapper;
import com.liaw.dev.Library.pix.EfiPixService;
import com.liaw.dev.Library.pix.PixDTO;
import com.liaw.dev.Library.repository.BookRepository;
import com.liaw.dev.Library.repository.LoanRepository;
import com.liaw.dev.Library.repository.UserRepository;
import com.liaw.dev.Library.validator.LoanValidator;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public JSONObject makeLoan(String registration, String isbn){
        validator.validateLoan(registration, isbn);
        User user = userRepository.findByRegistration(registration)
                .orElseThrow(()->new UserNotFoundException("Usuário não encontrado"));
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(()-> new BookNotFoundException("Livro não encontrado"));

        if(user.getBooks().size() == 8){
            throw new LoanMaxException("Limite de empréstimos atingido.");
        }
        String valorFormatado = book.getLoanPrice().setScale(2, RoundingMode.HALF_UP).toString();
        JSONObject pixResponseJson = pixService.pixCreateCharge(valorFormatado, user.getName(), user.getCpf());
        String txid = pixResponseJson.getString("txid");

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setFineAmount(book.getLoanPrice());
        loan.setStatus(LoanStatus.PENDING);
        loan.setPaymentStatus(PaymentStatus.PENDING);
        loan.setTxid(txid);
        repository.save(loan);

        return pixResponseJson;
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

    @Transactional
    public String processPayment(String txid){
        String status = pixService.checkPaymentStatus(txid);
        if ("CONCLUIDA".equalsIgnoreCase(status)){
            Loan loan = repository.findByTxid(txid)
                    .orElseThrow(()-> new LoanNotFoundException("Empréstimo não encontrado para o txid informado."));
            if (loan.getPaymentStatus() == PaymentStatus.PENDING){
                loan.setPaymentStatus(PaymentStatus.PAID);
                loan.setStatus(LoanStatus.ACTIVE);
                Book book = bookRepository.findByIdWithLoans(loan.getBook().getId())
                        .orElseThrow(()-> new BookNotFoundException("Livro não encontrado para o empréstimo."));
                User user = userRepository.findByIdWithBooks(loan.getUser().getId())
                        .orElseThrow(()-> new UserNotFoundException("Usuário não encontrado para o empréstimo."));

                book.setLoan(true);
                book.setUser(user);
                book.getLoans().add(loan);
                user.addBook(book);

                userRepository.save(user);
                bookRepository.save(book);
                System.out.println("Pagamento confirmado");
                return "Pagamento Confirmado. Empréstimo realizado com sucesso";
            }else {
                System.out.println("Empréstimo já estava feito");
                return "Empréstimo já estava feito";
            }
        }else {
            System.out.println("Pagamento pendente");
            return "Pagamento ainda PENDENTE ou com ERRO.";
        }
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void checkPendingPayment(){
        System.out.println("-----Começando a análise de pagamentos pendentes-----");
        List<Loan> pendingLoan = repository.findByPaymentStatus(PaymentStatus.PENDING);
        for (Loan loan:pendingLoan){
           this.processPayment(loan.getTxid());
        }
    }

}
