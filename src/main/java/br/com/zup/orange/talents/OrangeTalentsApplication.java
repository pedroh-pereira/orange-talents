package br.com.zup.orange.talents;

import java.time.LocalDate;
import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.zup.orange.talents.api.dto.AccountDTO;
import br.com.zup.orange.talents.api.dto.AddressDTO;
import br.com.zup.orange.talents.api.dto.CustomerDTO;
import br.com.zup.orange.talents.enums.EnumState;
import br.com.zup.orange.talents.enums.EnumStatus;
import br.com.zup.orange.talents.mappers.AccountMapper;
import br.com.zup.orange.talents.service.AccountService;
import br.com.zup.orange.talents.service.CustomerService;

@SpringBootApplication
public class OrangeTalentsApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrangeTalentsApplication.class, args);
	}

	@Bean
  CommandLineRunner init(CustomerService customerService, AccountService accountService, AccountMapper accountMapper) {
      return args -> {
          LongStream.range(1, 11)
                  .mapToObj(i -> {
											AddressDTO auxAddress = new AddressDTO("Rua teste "+i, Integer.parseInt(String.valueOf(i)), null, "Vila Teste" + i, "Cidade Teste" + i, EnumState.SP, String.valueOf(13035610L+i));
                      return new CustomerDTO(null, "TESTE " + i, "teste"+i+"@teste.com.br",String.valueOf(38112956324L+i), LocalDate.now(), EnumStatus.ACTIVE, auxAddress);
                  }).forEach(customerService::save);

          LongStream.range(1, 11)
                  .mapToObj(i -> new AccountDTO(null, new CustomerDTO(i), i,Short.valueOf("1"), i, Short.valueOf("1"), Double.valueOf(i > 1 ? i : 0), i > 1 ? EnumStatus.ACTIVE : EnumStatus.INACTIVE))
                  .forEach(accountService::save);
      };
  }

}
