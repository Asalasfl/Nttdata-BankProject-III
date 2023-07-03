package nttdata.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nttdata.com.deserializer.FluxDeserializer;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditDTO {
    private String idCredit;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal remainingAmount;
    private List<PaymentDTO> payments;
    private String messageDto;

    public CreditDTO(String messageDto){
        this.setMessageDto(messageDto);
    }
}