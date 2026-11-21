package br.com.brcoder.banking_core.customer.ports;

import java.util.Optional;

import br.com.brcoder.banking_core.customer.model.Customer;

public interface CustomerRepository {
  void insert(Customer customer);

  Optional<Customer> findByIdentitySubject(String identitySubject);
}
