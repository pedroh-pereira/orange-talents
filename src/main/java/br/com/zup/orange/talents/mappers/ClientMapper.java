package br.com.zup.orange.talents.mappers;

import org.mapstruct.Mapper;

import br.com.zup.orange.talents.dto.ClientDTO;
import br.com.zup.orange.talents.models.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
  Client toEntity(ClientDTO dto);
  ClientDTO toDTO(Client entity);
}
