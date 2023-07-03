package nttdata.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
/** type = deposit, withdrawal
 *
 */
public class TransactionDTO {
    private String transactionId;
    private String idCreditCard;
    private String idAccount;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String messageDto;

    public TransactionDTO(String messageDto){
        this.setMessageDto(messageDto);
    }
}