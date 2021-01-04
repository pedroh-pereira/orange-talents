package br.com.zup.orange.talents.exceptions;

public class RegisterAlreadyExistsException extends RuntimeException {
    
  private static final long serialVersionUID = 8217516288638241764L;

  public RegisterAlreadyExistsException(String message) {
    super(message);
  }
}
