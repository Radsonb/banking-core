package br.com.brcoder.banking_core.customer.model;

public class CustomerException extends RuntimeException {
  public enum Reason {
    INVALID_NAME,
    ALREADY_REGISTERED,
    NOT_FOUND
  }

  private final Reason reason;

  public CustomerException(Reason reason, String message) {
    super(message);
    this.reason = reason;
  }

  public Reason reason() {
    return reason;
  }
}
