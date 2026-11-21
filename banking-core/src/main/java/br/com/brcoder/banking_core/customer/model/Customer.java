package br.com.brcoder.banking_core.customer.model;

import java.time.Instant;
import java.util.UUID;

public record Customer(
  UUID id,
  String identitySubject,
  String fullName,
  CustomerStatus status,
  Instant createdAt
) {}
