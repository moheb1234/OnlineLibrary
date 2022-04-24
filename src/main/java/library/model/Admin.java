package library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import library.dependent_class.UserBookItem;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "admin")
public class Admin {
    private static Admin admin = null;
    private String firstname, lastname, username, password;
    @JsonIgnore
    private List<User> allUsers;
    @JsonIgnore
    private List<Book> allBooks;

    public Admin(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        allUsers = new ArrayList<>();
        allBooks = new ArrayList<>();
    }

    public static Admin getInstance() {
        return admin;
    }
//    public Book findBookByName(String bookName){
//        for (BookItem bookItem : allBooks) {
//
//        }
//    }

//    public boolean addBook(Book book, int number) {
//        for (Book book : allBooks) {
//            if (book.equals(book)) {
//                if (book.increaseNumOfBook(number))
//                    return true;
//            } else {
//                allBooks.add(new UserBookItem(book, number));
//                return true;
//            }
//        }
//        return false;
//    }
}
