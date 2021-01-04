package br.com.zup.orange.talents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.zup.orange.talents.api.dto.AccountDTO;
import br.com.zup.orange.talents.enums.EnumStatus;
import br.com.zup.orange.talents.exceptions.RegisterAlreadyExistsException;
import br.com.zup.orange.talents.exceptions.RegisterDeleteException;
import br.com.zup.orange.talents.exceptions.RegisterNotFoundException;
import br.com.zup.orange.talents.mappers.AccountMapper;
import br.com.zup.orange.talents.models.Account;
import br.com.zup.orange.talents.repository.AccountRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class AccountService{
  @Autowired
  AccountRepository repository;

  @Autowired
  AccountMapper mapper;

  public AccountDTO save(AccountDTO dto) throws RegisterAlreadyExistsException {
    Long count = repository.countByAgencyAndAccountNumber(dto.getAgency(), dto.getAccountNumber());
    if(count > 0) throw new RegisterAlreadyExistsException("Conta já cadastrada!");
    return mapper.toDTO(repository.save(mapper.toEntity(dto)));
  }

  public Page<AccountDTO> findFilters(AccountDTO dto, Pageable pageable){
    ExampleMatcher attributesMatcher = ExampleMatcher.matching()
    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("customer", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("agency", ExampleMatcher.GenericPropertyMatchers.contains())
    .withMatcher("digitAgency", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("accountNumber", ExampleMatcher.GenericPropertyMatchers.contains())
    .withMatcher("digitAccount", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("balance", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("accountStatus", ExampleMatcher.GenericPropertyMatchers.exact());

    final Account entity = mapper.toEntity(dto);
    return repository.findAll(Example.of(entity, attributesMatcher), pageable).map(mapper::toDTO);
  }

  public AccountDTO findById(final Long id) throws RegisterNotFoundException {
    return repository.findById(id).map(mapper::toDTO)
                     .orElseThrow(() -> new RegisterNotFoundException(String.format("Conta com id: %d não encontrada", id)));
  }

  public Account findByCustomerId(final Long idCustomer) {
    return repository.findByCustomerId(idCustomer);
  }

  public AccountDTO update(final AccountDTO dto, final Long id) throws RegisterNotFoundException {
    if(repository.countById(id) <= 0) throw new RegisterNotFoundException(String.format("Conta com id: %d não encontrada", id));
    return mapper.toDTO(repository.saveAndFlush(mapper.toEntity(dto)));
  }

  public void delete(Long id) throws RegisterNotFoundException, RegisterDeleteException {
    AccountDTO accountDTO = findById(id);
    if(accountDTO.getBalance() > 0) throw new RegisterDeleteException(String.format("Erro ao deletar conta, é necessário ter o saldo zerado. Saldo: %.2f", accountDTO.getBalance()));
    accountDTO.setAccountStatus(EnumStatus.DELETED);
    repository.save(mapper.toEntity(accountDTO));
  }
}
