package br.com.zup.orange.talents.exceptions;

public class RegisterNotFoundException extends RuntimeException {
  
  private static final long serialVersionUID = -3527078721784365803L;

  public RegisterNotFoundException(String message) {
    super(message);
  }  
}
