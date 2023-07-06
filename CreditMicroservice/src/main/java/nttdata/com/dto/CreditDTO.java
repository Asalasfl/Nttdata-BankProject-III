package nttdata.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditDTO {
    private String idCredit;
    private String idCustomer;
    private String type;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal remainingAmount;
    private List<PaymentDTO> payments;
    private String messageDto;

    public CreditDTO(String messageDto){
        this.setMessageDto(messageDto);
    }
}