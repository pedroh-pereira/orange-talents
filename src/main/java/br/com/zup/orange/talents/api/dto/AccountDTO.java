package br.com.zup.orange.talents.api.dto;

import br.com.zup.orange.talents.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
  private Long id;

  private CustomerDTO customer;

  private Long agency;

  private Short digitAgency;

  private Long accountNumber;

  private Short accountDigit;

  private Double balance;

  private EnumStatus accountStatus;
}
