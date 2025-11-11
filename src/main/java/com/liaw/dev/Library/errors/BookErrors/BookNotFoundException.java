package com.liaw.dev.Library.errors.BookErrors;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(String message) {
    super(message);
  }
}
