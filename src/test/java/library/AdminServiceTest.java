package library;

import com.sun.jdi.request.DuplicateRequestException;
import library.dependent_class.BookCategories;
import library.model.Book;
import library.repository.AdminRepository;
import library.repository.BookRepository;
import library.repository.UserRepository;
import library.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    BookRepository bookRepository;

    @Test
    void InCreateBookExpectedToThrowDuplicateExceptionWhenNameIsExist() {
        Book book = new Book("name", "moheb", BookCategories.ACTION, 2012, 3800, 3);
        when(bookRepository.findByName("name")).thenReturn(Optional.of(new Book()));
        assertThrows(DuplicateRequestException.class, () -> adminService.createBook(book));
    }

    @Test
    void inAddBookExpectedToThrowNotFoundWhenBookIsNotExist() {
        assertThrows(InstanceNotFoundException.class,() ->  adminService.addBook("id",2));
    }

    @Test
    void inAddBookExpectedToThrowIllegalStateExceptionWhenNumberIsZero() {
        when(bookRepository.findById("id")).thenReturn(Optional.of(new Book()));
        assertThrows(IllegalStateException.class,() ->  adminService.addBook("id",0));
    }

    @Test
    void successfullyAddBooks() {
        Book book = new Book(3,5000);
        when(bookRepository.findById("id")).thenReturn(Optional.of(book));
        adminService.addBook("id",2);
        assertEquals(book.getStock(),5);
    }

    @Test
    void SuccessfullyUpdateBookCost() {
        Book book = new Book(5000);
        when(bookRepository.findById("id")).thenReturn(Optional.of(book));
        adminService.updateBookCost("id",6500);
        assertEquals(book.getCost(),6500);
    }
}
