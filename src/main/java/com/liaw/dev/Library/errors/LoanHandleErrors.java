package com.liaw.dev.Library.errors;

import com.liaw.dev.Library.controller.Message;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanMaxException;
import com.liaw.dev.Library.errors.LoanErrors.LoanNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoanHandleErrors {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoanNotFoundException.class)
    public Message handleLoanNotFoundException(LoanNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(LoanMaxException.class)
    public Message handleLoanMaxException(LoanMaxException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(LoanWrongException.class)
    public Message handleLoanWrongException(LoanWrongException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

}
