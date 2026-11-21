package br.com.brcoder.banking_core.customer.wire;

import java.time.Instant;
import java.util.UUID;

import br.com.brcoder.banking_core.customer.model.Customer;

public record CustomerResponse(
  UUID id,
  String fullName,
  String status,
  Instant createdAt
) {
  public static CustomerResponse from(Customer customer) {
    return new CustomerResponse(
      customer.id(), 
      customer.fullName(),
      customer.status().name(),
      customer.createdAt()
    );
  }
}
