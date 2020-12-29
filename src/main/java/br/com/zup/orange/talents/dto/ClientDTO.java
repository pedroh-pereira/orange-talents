package br.com.zup.orange.talents.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO {
  private Long id;

  @NotBlank(message = "Nome nulo ou em branco")
  private String name;
  
  @NotBlank(message = "Email nulo ou em branco")
  @Email(message = "Email inválido")
  private String email;

  @NotBlank(message = "CPF nulo ou em branco")
  @CPF(message = "CPF inválido")
  private String cpf;
}
