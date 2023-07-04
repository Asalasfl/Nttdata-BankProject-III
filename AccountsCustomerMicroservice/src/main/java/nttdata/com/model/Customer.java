package nttdata.com.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String type;
    private Integer maxCredits;
    @Field("accountReferences")
    @DBRef
    private List<Account> accountReferences;
    @Field("creditReferences")
    @DBRef
    private List<Credit> creditReferences;
    @Field("creditCardReferences")
    @DBRef
    private List<CreditCard> creditCardReferences;
}
