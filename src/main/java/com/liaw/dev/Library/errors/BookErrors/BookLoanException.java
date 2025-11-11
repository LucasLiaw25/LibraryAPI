package com.liaw.dev.Library.errors.BookErrors;

public class BookLoanException extends RuntimeException {
    public BookLoanException(String message) {
        super(message);
    }
}
