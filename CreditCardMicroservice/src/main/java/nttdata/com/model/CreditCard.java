package nttdata.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "creditCards")

/**A credit card is a generic concept that can have different types, such as personal credit card, business credit card.
 *
 */
public class CreditCard {
    @Id
    private String id;
    private String customerId;
    private String type;
    private BigDecimal creditLimit;
    private BigDecimal currentBalance;
    @Field("transactionReferences")
    private List<Transaction> transactionReferences; // List of transaction IDs
}
