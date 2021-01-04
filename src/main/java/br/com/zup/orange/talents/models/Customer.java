package br.com.zup.orange.talents.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.zup.orange.talents.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
@SequenceGenerator(name = "customers_seq_generator", sequenceName = "seq_customers", allocationSize = 1)
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq_generator")
  @Column(name = "customer_id")
  private Long id;

  private String name;

  private String email;

  private String cpf;

  private LocalDate birthday;

  private EnumStatus status;

  @Embedded
  private Address address;
}
