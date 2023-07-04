package nttdata.com.utils;

import nttdata.com.dto.*;
import nttdata.com.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static nttdata.com.utils.AccountConverter.accountToAccountDTO;
import static nttdata.com.utils.CreditCardConverter.creditCardToDTO;
import static nttdata.com.utils.CreditConverter.creditToDTO;
import static nttdata.com.utils.TransactionConverter.transactionToTransactionDTO;

public class    CustomerConverter {

    // Metodo que convierte un Customer Entity a un Customer Dto
    public static CustomerDTO customerToCustomerDTO(Customer entity){
        CustomerDTO dto = new CustomerDTO();
        dto.setIdCustomer(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());

        List<AccountDTO> accountsDTOs = new ArrayList<>();
        if (entity.getAccountReferences()!= null) {
            for (Account account : entity.getAccountReferences()) {
                AccountDTO accountDTO = accountToAccountDTO(account);
                accountsDTOs.add(accountDTO);
            }
        }
        dto.setAccounts(accountsDTOs);

        List<CreditDTO> creditDTOS = new ArrayList<>();
        if (entity.getCreditReferences()!= null) {
            for (Credit credit : entity.getCreditReferences()) {
                CreditDTO creditDTO = creditToDTO(credit);
                creditDTOS.add(creditDTO);
            }
        }
        dto.setCredits(creditDTOS);

        List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
        if (entity.getCreditCardReferences()!= null) {
            for (CreditCard creditCard : entity.getCreditCardReferences()) {
                CreditCardDTO creditCardDTO = creditCardToDTO(creditCard);
                creditCardDTOS.add(creditCardDTO);
            }
        }
        dto.setCreditCards(creditCardDTOS);
        return dto;
    }

    // Metodo que convierte un Customer Dto a un Customer Entity
    public static Customer customerDTOToCustomer(CustomerDTO dto) {
        Customer entity = new Customer();
        entity.setId(dto.getIdCustomer());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        if (dto.getType().equalsIgnoreCase("personal")) {
            entity.setMaxCredits(1);
        } else if (dto.getType().equalsIgnoreCase("empresarial")) {
            entity.setMaxCredits(Integer.MAX_VALUE); // Cliente empresarial puede tener múltiples créditos
        }
        return entity;
    }
}