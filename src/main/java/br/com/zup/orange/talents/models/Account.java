package br.com.zup.orange.talents.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.zup.orange.talents.enums.EnumStatus;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
@SequenceGenerator(name = "accounts_seq_generator", sequenceName = "seq_accounts", allocationSize = 1)
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_seq_generator")
  @Column(name = "account_id")
  private Long id;

  @JoinColumn(name = "customer_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  private Long agency;

  @Column(name = "agency_digit")
  private Short digitAgency;

  @Column(name = "number")
  private Long accountNumber;

  @Column(name = "account_digit")
  private Short accountDigit;

  private Double balance;

  @Column(name = "status")
  private EnumStatus accountStatus;
}
