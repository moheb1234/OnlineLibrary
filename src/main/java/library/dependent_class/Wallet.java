package library.dependent_class;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Wallet {
    @Id
    private int walletNumber;
    private int balance;

    public Wallet(int balance) {
        this.balance = balance;
    }

    public boolean deposit(int amount) {
        if (amount > 0) {
            balance = amount + balance;
            return true;
        }
        return false;
    }

    public boolean withdrawal(int amount) {
        if (amount > balance) {
            return false;
        }
        balance = balance - amount;
        return true;
    }
}
