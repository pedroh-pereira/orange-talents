package br.com.zup.demo.mappers;

import org.mapstruct.Mapper;

import br.com.zup.demo.dto.ClientDTO;
import br.com.zup.demo.models.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
  Client toEntity(ClientDTO dto);
  ClientDTO toDTO(Client entity);
}
