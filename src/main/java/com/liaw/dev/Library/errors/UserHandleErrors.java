package com.liaw.dev.Library.errors;

import com.liaw.dev.Library.controller.Message;
import com.liaw.dev.Library.errors.BookErrors.BookNotFoundException;
import com.liaw.dev.Library.errors.UserErrors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserHandleErrors {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Message handleUserNotFoundException(UserNotFoundException e){
        return new Message(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
    }

}
