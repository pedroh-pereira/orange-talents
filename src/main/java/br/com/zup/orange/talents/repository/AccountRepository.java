package br.com.zup.orange.talents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.orange.talents.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
  public Long countByAgencyAndAccountNumber(Long agency, Long number);
  public Long countById(Long id);
  public Account findByCustomerId(Long id);
}
