package br.com.brcoder.banking_core.customer.adapters.persistence;

import br.com.brcoder.banking_core.customer.model.Customer;
import br.com.brcoder.banking_core.customer.model.CustomerException;
import br.com.brcoder.banking_core.customer.model.CustomerStatus;
import br.com.brcoder.banking_core.customer.ports.CustomerRepository;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

  private final JdbcClient jdbc;

  public JdbcCustomerRepository(JdbcClient jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public void insert(Customer customer) {
    int inserted = jdbc.sql("""
        INSERT INTO customers (id, identity_subject, full_name, status, created_at)
        VALUES (:id, :subject, :fullName, :status, :createdAt)
        ON CONFLICT (identity_subject) DO NOTHING
        """)
        .param("id", customer.id())
        .param("subject", customer.identitySubject())
        .param("fullName", customer.fullName())
        .param("status", customer.status().name())
        .param(
            "createdAt",
            customer.createdAt().atOffset(ZoneOffset.UTC))
        .update();

    if (inserted == 0) {
      throw new CustomerException(
          CustomerException.Reason.ALREADY_REGISTERED,
          "Identidade já vinculada a um cliente");
    }
  }

  @Override
  public Optional<Customer> findByIdentitySubject(String subject) {
    return jdbc.sql("""
        SELECT id, identity_subject, full_name, status, created_at
        FROM customers
        WHERE identity_subject = :subject
        """)
        .param("subject", subject)
        .query((rs, rowNum) -> new Customer(
            rs.getObject("id", UUID.class),
            rs.getString("identity_subject"),
            rs.getString("full_name"),
            CustomerStatus.valueOf(rs.getString("status")),
            rs.getObject(
                "created_at",
                OffsetDateTime.class).toInstant()))
        .optional();
  }
}