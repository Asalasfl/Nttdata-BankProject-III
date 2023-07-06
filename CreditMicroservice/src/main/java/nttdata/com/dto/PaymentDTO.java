package nttdata.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    private String idPayment;
    private String idCredit;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String messageDto;

    public PaymentDTO(String messageDto){
        this.setMessageDto(messageDto);
    }
}