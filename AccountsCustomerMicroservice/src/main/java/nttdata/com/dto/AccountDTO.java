package nttdata.com.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    private String idAccount;
    private String type;
    private BigDecimal balance;
    private List<TransactionDTO> transactions;
    private boolean commissionFree;
    private boolean limitMovement;
    private Integer maxMonthlyMovements;
    private String messageDto;
    public AccountDTO(String messageDto){this.setMessageDto(messageDto);}
}