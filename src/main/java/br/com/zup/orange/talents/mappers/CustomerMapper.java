package br.com.zup.orange.talents.mappers;

import org.mapstruct.Mapper;

import br.com.zup.orange.talents.api.dto.CustomerDTO;
import br.com.zup.orange.talents.models.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<Customer, CustomerDTO> {
}
