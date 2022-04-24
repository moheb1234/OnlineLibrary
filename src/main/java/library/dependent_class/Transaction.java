package library.dependent_class;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Transaction {
    private int referenceCode;
    private int amount;
    private String explain;
    private Date date;

    public Transaction(int amount, String explain) {
        referenceCode = (int) (Math.random() * (900000000) + 100000000);
        this.amount = amount;
        this.explain = explain;
        date = new Date();
    }
}
