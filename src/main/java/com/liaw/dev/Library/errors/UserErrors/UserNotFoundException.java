package com.liaw.dev.Library.errors.UserErrors;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
