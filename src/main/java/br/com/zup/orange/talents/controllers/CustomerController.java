package br.com.zup.orange.talents.controllers;

import java.time.LocalDate;

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

import br.com.zup.orange.talents.api.dto.AddressDTO;
import br.com.zup.orange.talents.api.dto.CustomerDTO;
import br.com.zup.orange.talents.enums.EnumState;
import br.com.zup.orange.talents.enums.EnumStatus;
import br.com.zup.orange.talents.exceptions.RegisterAlreadyExistsException;
import br.com.zup.orange.talents.exceptions.RegisterDeleteException;
import br.com.zup.orange.talents.exceptions.RegisterNotFoundException;
import br.com.zup.orange.talents.service.CustomerService;

/**
 * ClientController
 */

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

  @Autowired
  private CustomerService service;

  @PostMapping
  public ResponseEntity<CustomerDTO> create(UriComponentsBuilder uriComponentsBuilder,
      @RequestBody @Valid CustomerDTO dto) throws RegisterAlreadyExistsException {
    dto = service.save(dto);
    UriComponents uriComponents = uriComponentsBuilder.path("/{id}").buildAndExpand(dto.getId());
    return ResponseEntity.created(uriComponents.toUri()).body(dto);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<CustomerDTO> findById(@PathVariable("id") @Min(1) final Long id)
      throws RegisterNotFoundException {
    return ResponseEntity.ok().body(service.findById(id));
  }

  @GetMapping
  public ResponseEntity<Page<CustomerDTO>> findFilters(
      @RequestParam(name = "customer_id", required = false) final Long id,
      @RequestParam(name = "name", required = false) final String name,
      @RequestParam(name = "email", required = false) final String email,
      @RequestParam(name = "cpf", required = false) final String cpf,
      @RequestParam(name = "birthday", required = false) final LocalDate birthday,
      @RequestParam(name = "status", required = false) final EnumStatus status,
      @RequestParam(name = "street", required = false) final String street,
      @RequestParam(name = "number", required = false) final Integer number,
      @RequestParam(name = "complement", required = false) final String complement,
      @RequestParam(name = "neighborhood", required = false) final String neighborhood,
      @RequestParam(name = "city", required = false) final String city,
      @RequestParam(name = "state", required = false) final EnumState state,
      @RequestParam(name = "zip_code", required = false) final String zipCode, final Pageable pageable) {
    CustomerDTO customerDTO = new CustomerDTO(id, name, email, cpf, birthday, status,
        new AddressDTO(street, number, complement, neighborhood, city, state, zipCode));
    return ResponseEntity.ok().body(service.findFilters(customerDTO, pageable));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO dto, @PathVariable("id") @Min(1) final Long id)
      throws RegisterNotFoundException {
    return ResponseEntity.ok().body(service.update(dto, id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") @Min(1) final Long id)
      throws RegisterNotFoundException, RegisterDeleteException, RegisterAlreadyExistsException {
      service.delete(id);
      return ResponseEntity.noContent().build();
  }
  
  
}