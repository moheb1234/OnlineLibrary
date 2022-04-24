package library.service;

import com.sun.jdi.request.DuplicateRequestException;
import library.model.Book;
import library.repository.BookRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import static library.error_handling.ErrorMessage.*;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements BookServiceInterface {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(Book book) {
        Optional<Book> bookOptional = bookRepository.findByName(book.getName());
        if (bookOptional.isPresent()) {
            throw new DuplicateRequestException(DUPLICATE_BOOK_NAME);
        }
        return bookRepository.insert(book);
    }

    @Override
    public Book add(String id, int number) {
        Book book = findById(id);
        if (book.add(number)) {
            bookRepository.save(book);
            return book;
        }
        throw new IllegalStateException(INVALID_NUMBER);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Book findById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new InstanceNotFoundException(BOOK_NOTFOUND);
        }
        return bookOptional.get();
    }

    @Override
    public Book updateCost(double cost, String id) {
        Book book = findById(id);
        if (book.updateCost(cost)) {
            return bookRepository.save(book);
        }
        throw new IllegalStateException(INVALID_COST);
    }

    @Override
    public Book delete(String id) {
        Book book = findById(id);
        bookRepository.deleteById(id);
        return book;
    }
}
