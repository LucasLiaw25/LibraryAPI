package com.liaw.dev.Library.errors.LoanErrors;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
