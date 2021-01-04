package br.com.zup.orange.talents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.orange.talents.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
  public Long countByNameOrCpf(String name, String cpf);
  public Long countById(Long id);
}
