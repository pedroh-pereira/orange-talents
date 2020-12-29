package br.com.zup.orange.talents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.orange.talents.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
}
