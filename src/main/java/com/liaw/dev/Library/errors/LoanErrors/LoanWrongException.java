package com.liaw.dev.Library.errors.LoanErrors;

public class LoanWrongException extends RuntimeException {
    public LoanWrongException(String message) {
        super(message);
    }
}
