package br.com.brcoder.banking_core.customer.adapters.http;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import br.com.brcoder.banking_core.customer.model.Customer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brcoder.banking_core.customer.contoller.CustomerUseCases;
import br.com.brcoder.banking_core.customer.wire.CreateCustomerRequest;
import br.com.brcoder.banking_core.customer.wire.CustomerResponse;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerUseCases useCases;

  public CustomerController(CustomerUseCases useCases) {
    this.useCases = useCases;
  }

  @PostMapping 
  public ResponseEntity<CustomerResponse> register(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody CreateCustomerRequest request
  ) {
    Customer customer = useCases.register(jwt.getSubject(),request.fullName());

    return ResponseEntity.created(URI.create("/customers/me")).body(CustomerResponse.from(customer));
  }

  @GetMapping("/me")
  public CustomerResponse me(@AuthenticationPrincipal Jwt jwt) {
    return CustomerResponse.from(useCases.findCurrentCustomer(jwt.getSubject()));
  }
}
