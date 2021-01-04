package br.com.zup.orange.talents.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.zup.orange.talents.api.dto.CustomerDTO;
import br.com.zup.orange.talents.enums.EnumStatus;
import br.com.zup.orange.talents.exceptions.RegisterAlreadyExistsException;
import br.com.zup.orange.talents.exceptions.RegisterDeleteException;
import br.com.zup.orange.talents.exceptions.RegisterNotFoundException;
import br.com.zup.orange.talents.mappers.CustomerMapper;
import br.com.zup.orange.talents.models.Account;
import br.com.zup.orange.talents.models.Customer;
import br.com.zup.orange.talents.repository.CustomerRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CustomerService {
  @Autowired
  CustomerRepository repository;

  @Autowired
  CustomerMapper mapper;

  @Autowired
  AccountService accountService;

  public CustomerDTO save(CustomerDTO dto) throws RegisterAlreadyExistsException {
    Customer entity = mapper.toEntity(dto);
    Long count = repository.countByNameOrCpf(entity.getName(), entity.getCpf());
    if(count > 0) throw new RegisterAlreadyExistsException("Cliente já cadastrado!");
    return mapper.toDTO(repository.save(entity));
  }

  public Page<CustomerDTO> findFilters(CustomerDTO dto, Pageable pageable){
    final Customer entity = mapper.toEntity(dto);
    
    ExampleMatcher attributesMatcher = ExampleMatcher.matching()
    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("cpf", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("birthday", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("address.street", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withMatcher("address.number", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("address.complement", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withMatcher("address.neighborhood", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withMatcher("address.city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    .withMatcher("address.state", ExampleMatcher.GenericPropertyMatchers.exact())
    .withMatcher("address.zipCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

    return repository.findAll(Example.of(entity, attributesMatcher), pageable).map(mapper::toDTO);
  }

  public CustomerDTO findById(final Long id) throws RegisterNotFoundException {
    return repository.findById(id).map(mapper::toDTO)
                     .orElseThrow(() -> new RegisterNotFoundException(String.format("Cliente com id: %d não encontrada", id)));
  }

  public CustomerDTO update(final CustomerDTO dto, final Long id) throws RegisterNotFoundException {
    if(repository.countById(id) <= 0) throw new RegisterNotFoundException(String.format("Cliente com id: %d não encontrado", id));
    return mapper.toDTO(repository.saveAndFlush(mapper.toEntity(dto)));
  }

  public void delete(Long id) throws RegisterDeleteException,
      RegisterAlreadyExistsException {
    Customer currentCustomer = repository.findById(id)
                                         .orElseThrow(() -> new RegisterAlreadyExistsException(String.format("Cliente com id: %d não encontrado", id)));
    Account account = accountService.findByCustomerId(id);
    if(Objects.nonNull(account) && account.getAccountStatus().equals(EnumStatus.ACTIVE)) throw new RegisterDeleteException("Erro ao deletar cliente, pois uma conta vinculada está ativa");

    currentCustomer.setStatus(EnumStatus.DELETED);
    repository.saveAndFlush(currentCustomer);
  }
}
