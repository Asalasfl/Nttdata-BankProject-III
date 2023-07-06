package nttdata.com.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String type; // "PERSONAL" or "BUSINESS"
    @Field("accountReferences")
    private List<Account> accountReferences;
    @Field("creditReferences")
    private List<Credit> creditReferences;
    @Field("creditCardReferences")
    private List<CreditCard> creditCardReferences;
}
