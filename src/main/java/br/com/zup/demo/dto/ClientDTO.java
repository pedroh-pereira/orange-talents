package br.com.zup.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO {
  private Long id;

  @NotBlank(message = "Nome nulo ou em branco")
  private String name;
  
  @NotBlank(message = "Email nulo ou em branco")
  @Pattern(message = "Email inválido", regexp="\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b")
  private String email;

  @NotBlank(message = "CPF nulo ou em branco")
  @Pattern(message = "CPF inválido", regexp = "^(([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}))$")
  private String cpf;
}
