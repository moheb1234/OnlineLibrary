package library.service;

import library.model.Admin;
import library.model.Book;
import library.model.User;
import library.repository.AdminRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import static library.error_handling.ErrorMessage.*;
import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements AdminServiceInterface {

    private final AdminRepository adminRepository;
    private final BookService bookService;
    private final UserService userService;


    public AdminService(AdminRepository adminRepository, BookService bookService, UserService userService) {
        this.adminRepository = adminRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public Admin SignIn(String username, String password) {
        Optional<Admin> optionalAdmin = adminRepository.findByUsernameAndPassword(username, password);
        if (optionalAdmin.isEmpty()) {
            throw new LoginException(LOGIN_ERROR);
        }
        return optionalAdmin.get();
    }

    @Override
    public Book createBook(Book book) {
        return bookService.create(book);
    }

    @Override
    public Book addBook(String bookId, int number) {
        return bookService.add(bookId, number);
    }

    @Override
    public Book deleteBook(String bookId) {
        return bookService.delete(bookId);
    }

    @Override
    public Book updateBookCost(String bookId, double newCost) {
        return bookService.updateCost(newCost, bookId);
    }

    @Override
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }
}
