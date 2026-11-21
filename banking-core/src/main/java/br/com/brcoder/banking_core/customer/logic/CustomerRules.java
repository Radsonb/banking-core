package br.com.brcoder.banking_core.customer.logic;

import java.time.Instant;
import java.util.UUID;

import br.com.brcoder.banking_core.customer.model.Customer;
import br.com.brcoder.banking_core.customer.model.CustomerException;
import br.com.brcoder.banking_core.customer.model.CustomerStatus;

public class CustomerRules {
  private CustomerRules() {}

  public static Customer register(
    UUID id,
    String identitySubject,
    String fullName,
    Instant createdAt
  ) {
    String normalizedName =
      fullName == null ? "" : fullName.strip();

    if (normalizedName.length() < 3
      || normalizedName.length() > 150) {
        throw new CustomerException(
          CustomerException.Reason.INVALID_NAME, 
        "Nome deve conter entre 3 e 150 caracteres"
        );
      }

    return new Customer(
      id, 
      identitySubject, 
      fullName, 
      CustomerStatus.PENDING_VERIFICATION, 
      createdAt
    );
  }
}
