package library.service;


import library.dependent_class.UserBookItem;
import library.model.Admin;
import library.model.Book;
import library.model.User;

import java.util.List;

public interface AdminServiceInterface {

    Admin SignIn(String username , String password);
    List<User> getAllUsers();
    List<Book> getAllBooks();
    Book addBook(String bookId , int number);
    Book createBook(Book book);
    Book deleteBook(String bookId);
    Book updateBookCost(String bookId , double newCost);

}
