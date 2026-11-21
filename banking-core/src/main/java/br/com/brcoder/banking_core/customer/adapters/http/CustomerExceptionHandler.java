package br.com.brcoder.banking_core.customer.adapters.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.brcoder.banking_core.customer.model.CustomerException;

@RestControllerAdvice(assignableTypes = CustomerController.class)
public class CustomerExceptionHandler {
  @ExceptionHandler(CustomerException.class)
  public ResponseEntity<ProblemDetail> handle(CustomerException exception) {
    HttpStatus status = switch (exception.reason()) {
      case INVALID_NAME -> HttpStatus.BAD_REQUEST;
      case ALREADY_REGISTERED -> HttpStatus.CONFLICT;
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
    };

    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        status,
        exception.getMessage());

    problem.setProperty("code", exception.reason().name());

    return ResponseEntity.status(status).body(problem);
  }
}
