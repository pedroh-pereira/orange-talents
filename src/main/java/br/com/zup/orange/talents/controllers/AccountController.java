package br.com.zup.orange.talents.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.orange.talents.api.dto.AccountDTO;
import br.com.zup.orange.talents.api.dto.CustomerDTO;
import br.com.zup.orange.talents.enums.EnumStatus;
import br.com.zup.orange.talents.exceptions.RegisterAlreadyExistsException;
import br.com.zup.orange.talents.exceptions.RegisterDeleteException;
import br.com.zup.orange.talents.exceptions.RegisterNotFoundException;
import br.com.zup.orange.talents.service.AccountService;

/**
 * ClientController
 */

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

  @Autowired
  private AccountService service;

  @PostMapping
  public ResponseEntity<AccountDTO> create(UriComponentsBuilder uriComponentsBuilder,
      @RequestBody @Valid AccountDTO dto) throws RegisterAlreadyExistsException {
    dto = service.save(dto);
    UriComponents uriComponents = uriComponentsBuilder.path("/{id}").buildAndExpand(dto.getId());
    return ResponseEntity.created(uriComponents.toUri()).body(dto);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<AccountDTO> findById(@PathVariable("id") @Min(1) final Long id)
      throws RegisterNotFoundException {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @GetMapping
  public ResponseEntity<Page<AccountDTO>> findFilters(
      @RequestParam(name = "account_id", required = false) final Long id,
      @RequestParam(name = "customer_id", required = false) final Long idCustomer,
      @RequestParam(name = "agency", required = false) final Long agency,
      @RequestParam(name = "agency_digit", required = false) final Short agencyDigit,
      @RequestParam(name = "account_number", required = false) final Long accountNumber,
      @RequestParam(name = "account_digit", required = false) final Short accountDigit,
      @RequestParam(name = "account_balance", required = false) final Double accountBalance,
      @RequestParam(name = "account_status", required = false) final EnumStatus accountStatus,
      final Pageable pageable) {

    AccountDTO accountDTO = new AccountDTO(id, new CustomerDTO(idCustomer), agency, agencyDigit, accountNumber,
        accountDigit, accountBalance, accountStatus);
    return ResponseEntity.ok().body(service.findFilters(accountDTO, pageable));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO dto, @PathVariable("id") @Min(1) final Long id)
      throws RegisterNotFoundException {
    return ResponseEntity.ok().body(service.update(dto, id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") @Min(1) final Long id)
      throws RegisterNotFoundException, RegisterDeleteException {
      service.delete(id);
      return ResponseEntity.noContent().build();
  }
  
  
}