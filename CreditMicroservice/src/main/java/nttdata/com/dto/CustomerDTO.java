package nttdata.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private String idCustomer;
    private String name;
    private String type;
    private List<String> accounts;
    private List<CreditDTO> credits;
    private List<String> creditCards;
    private String messageDto;

    public CustomerDTO(String messageDto){
        this.setMessageDto(messageDto);
    }
}