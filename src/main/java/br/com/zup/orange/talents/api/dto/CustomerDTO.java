package br.com.zup.orange.talents.api.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.br.CPF;

import br.com.zup.orange.talents.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

  private Long id;

  @NotBlank(message = "Nome nulo ou em branco")
  private String name;
  
  @NotBlank(message = "Email nulo ou em branco")
  @Email(message = "Email inválido")
  private String email;

  @NotBlank(message = "CPF nulo ou em branco")
  @CPF(message = "CPF inválido")
  private String cpf;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  private EnumStatus status;

  @Valid
  @NotNull
  private AddressDTO address;

  public CustomerDTO(Long id) {
    this.id = id;
  }
}
