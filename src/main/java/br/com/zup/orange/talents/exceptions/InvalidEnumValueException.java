package br.com.zup.orange.talents.exceptions;

public class InvalidEnumValueException extends RuntimeException {

  private static final long serialVersionUID = -2996547506648645629L;

  public InvalidEnumValueException(String message) {
    super(message);
  }
  
}
