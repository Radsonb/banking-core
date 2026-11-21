package br.com.brcoder.banking_core.customer.contoller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brcoder.banking_core.customer.logic.CustomerRules;
import br.com.brcoder.banking_core.customer.model.Customer;
import br.com.brcoder.banking_core.customer.model.CustomerException;
import br.com.brcoder.banking_core.customer.ports.CustomerRepository;

@Service
public class CustomerUseCases {
  private final CustomerRepository repository;

  public CustomerUseCases(CustomerRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Customer register(String identitySubject, String fullName) {
    Customer customer = CustomerRules.register(
        UUID.randomUUID(),
        identitySubject,
        fullName,
        Instant.now().truncatedTo(ChronoUnit.MICROS));

    repository.insert(customer);

    return customer;
  }

  @Transactional(readOnly = true)
  public Customer findCurrentCustomer(String identitySubject) {
    return repository.findByIdentitySubject(identitySubject)
        .orElseThrow(() -> new CustomerException(
            CustomerException.Reason.NOT_FOUND,
            "Cadastro de cliente não encontrado"));
  }
}
