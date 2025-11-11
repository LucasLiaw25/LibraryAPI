package com.liaw.dev.Library.errors;

import com.liaw.dev.Library.controller.Message;
import com.liaw.dev.Library.errors.BookErrors.BookLoanException;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.errors.LoanErrors.LoanMaxException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookHandleErrors {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public Message handleBookNotFoundException(BookNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BookLoanException.class)
    public Message handleBookLoanException(BookLoanException e){
        return new Message(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
    }

}
