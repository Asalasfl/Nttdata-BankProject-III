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
@Document(collection = "accounts")

/** bank account is a generic class, for common base for all saving account,
 *  fixedAccount with common methods and properties  for all accounts,
 *  this is abstract class for inheritors to use their own implementation
 *  -"SAVINGS", "CHECKING", "FIXED_TERM" for String Type
 *  - List of transaction IDs
 */
public class Account {
    @Id
    private String id;
    private Customer customerId;
    private String type;
    private BigDecimal balance;
    @Field("accountReferences")
    private List<Transaction> accountReferences;
}
