package library.model;

import library.dependent_class.Address;
import library.dependent_class.Transaction;
import library.dependent_class.UserBookItem;
import library.dependent_class.Wallet;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Data
@Document(collection = "user")
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String firstname, lastname, username, password, phoneNumber;
    private Address address;
    private int age;
    private Wallet wallet;
    private List<Transaction> transactions;
    private List<UserBookItem> books;

    public User(String firstname, String lastname, String username, String password, String phoneNumber, Address address, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
        wallet = new Wallet(0);
        books = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public User(Wallet wallet) {
        this.wallet = wallet;
        books = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public static int calcTotalCost(List<UserBookItem> bookItems) {
        int totalCost = 0;
        for (UserBookItem bookItem : bookItems) {
            totalCost += bookItem.getBook().getCost() * bookItem.getNumber();
        }
        return totalCost;
    }

    public boolean ageIsValid() {
        return 3 < age && age < 100;
    }

    public boolean usernameIsValid() {
        return Pattern.matches("\\w+", username);
    }

    public boolean passwordIsValid() {
        if (password.length() < 10) {
            return false;
        }
        boolean condition1 = Pattern.matches("\\w+", password);
        boolean condition2 = Pattern.matches("\\p{Punct}+", password);
        return !(condition1 || condition2);
    }

    public boolean phoneIsValid() {
        return Pattern.matches("^09[0-9]{9}", phoneNumber);
    }

    public boolean firstnameIsValid() {
        return Pattern.matches("[a-zA-Z]+", firstname);
    }

    public boolean lastnameIsValid() {
        return Pattern.matches("[a-zA-Z]+", lastname);
    }

    public Book findBookById(String bookId) {
        for (UserBookItem bookItem : books) {
            if (bookItem.getBook().getId().equals(bookId))
                return bookItem.getBook();
        }
        return null;
    }

    public Book findBookByName(String bookName) {
        for (UserBookItem bookItem : books) {
            if (bookItem.getBook().getName().equals(bookName))
                return bookItem.getBook();
        }
        return null;
    }

    public void addBooks(List<UserBookItem> bookItems) {
        if (books.isEmpty()) {
            books.addAll(bookItems);
            return;
        }
        for (UserBookItem bookItem : bookItems) {
            for (UserBookItem book : books) {
                if (bookItem.getBook().equals(book.getBook())) {
                    book.setNumber(book.getNumber() + bookItem.getNumber());
                    break;
                } else {
                    books.add(bookItem);
                }
            }
        }
    }

    public boolean buyBooks(List<UserBookItem> selectedBooks) {
        int totalCost = calcTotalCost(selectedBooks);
        if (!wallet.withdrawal(totalCost))
            return false;
        Transaction transaction = new Transaction(totalCost, "");
        transactions.add(transaction);
        addBooks(selectedBooks);
        return true;
    }
}
