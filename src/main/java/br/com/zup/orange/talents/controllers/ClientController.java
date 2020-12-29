package br.com.zup.orange.talents.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orange.talents.dto.ClientDTO;
import br.com.zup.orange.talents.mappers.ClientMapper;
import br.com.zup.orange.talents.models.Client;
import br.com.zup.orange.talents.repository.ClientRepository;

/**
 * ClientController
 */
 
@RestController("/clients")
public class ClientController {
  private ClientRepository repository;

  @Autowired
  private ClientMapper mapper;

  public ClientController(ClientRepository clientRepository) {
    this.repository = clientRepository;
  }

  @PostMapping
  public ResponseEntity<Client> create(@RequestBody @Validated ClientDTO dto) {
          Client savedItem = repository.save(mapper.toEntity(dto));
          return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Client>> getAll() {
      List<Client> items = new ArrayList<>();
      repository.findAll().forEach(items::add);
      if (items.isEmpty())
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
  }
  
  
}