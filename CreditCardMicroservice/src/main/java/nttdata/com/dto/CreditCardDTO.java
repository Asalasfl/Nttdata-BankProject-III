package nttdata.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDTO {
    private String creditCardId;
    private BigDecimal creditLimit;
    private BigDecimal currentBalance;
    private List<TransactionDTO> transactions;
    private String messageDto;

    public CreditCardDTO(String messageDto){
        this.setMessageDto(messageDto);
    }
}