package br.com.zup.orange.talents.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.zup.orange.talents.enums.EnumState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

  @NotBlank
  @Size(min = 3, max = 100)
  private String street;

  private Integer number;

  private String complement;

  @NotBlank
  @Size(min = 3, max = 100)
  private String neighborhood;

  @NotBlank
  @Size(min = 3, max = 100)
  private String city;

  @NotNull
  private EnumState state;

  @NotBlank
  @Size(min = 8, max = 8)
  private String zipCode;
}
