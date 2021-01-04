package br.com.zup.orange.talents.handler;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.zup.orange.talents.api.dto.ErrorDTO;
import br.com.zup.orange.talents.exceptions.RegisterAlreadyExistsException;
import br.com.zup.orange.talents.exceptions.RegisterDeleteException;
import br.com.zup.orange.talents.exceptions.RegisterNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  
  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<Map<String, String>> errorList = ex.getBindingResult().getAllErrors().stream()
                                        .map(error -> Map.of(((FieldError) error).getField(), error.getDefaultMessage()))
                                        .collect(Collectors.toList());
    ErrorDTO errorDTO = new ErrorDTO().setMessage("Request inválido, verifique os campos e envie novamente!").setErrors(errorList);
    return handleExceptionInternal(ex, errorDTO, headers, status, request);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
    RegisterAlreadyExistsException.class,
    RegisterNotFoundException.class,
    RegisterDeleteException.class,
  })
  protected ResponseEntity<Object> handleRegistersException(Exception ex, WebRequest request){
    ErrorDTO errorDTO = new ErrorDTO().setMessage(ex.getMessage());
    return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
      ErrorDTO auxBody = Optional.ofNullable(body).filter(obj -> !(obj instanceof ErrorDTO)).map(obj -> new ErrorDTO().setMessage(status.getReasonPhrase()))
                                              .orElse((ErrorDTO) body);
      auxBody.setTimestamp(Instant.now())
             .setPath(request.getContextPath())
             .setStatus(status.value())
             .setMethod(((ServletWebRequest) request).getHttpMethod());

    return super.handleExceptionInternal(ex, auxBody, headers, status, request);
  }

}
