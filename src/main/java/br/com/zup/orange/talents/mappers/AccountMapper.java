package br.com.zup.orange.talents.mappers;

import org.mapstruct.Mapper;

import br.com.zup.orange.talents.api.dto.AccountDTO;
import br.com.zup.orange.talents.models.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<Account, AccountDTO>{
}
