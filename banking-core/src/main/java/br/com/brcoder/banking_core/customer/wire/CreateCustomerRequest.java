package br.com.brcoder.banking_core.customer.wire;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
  @NotBlank 
  @Size(min = 3, max = 150)
  String fullName
) {}
