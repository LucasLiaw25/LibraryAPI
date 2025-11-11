package com.liaw.dev.Library.errors.LoanErrors;

public class LoanMaxException extends RuntimeException {
    public LoanMaxException(String message) {
        super(message);
    }
}
