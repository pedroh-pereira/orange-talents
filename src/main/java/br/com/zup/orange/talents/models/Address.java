package br.com.zup.orange.talents.models;

import javax.persistence.Embeddable;

import br.com.zup.orange.talents.enums.EnumState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

  private String street;

  private Integer number;
  
  private String complement;

  private String neighborhood;

  private String city;

  private EnumState state;

  private String zipCode; 

}
