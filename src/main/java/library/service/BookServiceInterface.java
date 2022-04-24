package library.service;

import library.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookServiceInterface {

    Book findById(String id);

    Book create(Book book);

    Book add(String id, int number);

    List<Book> findAll();

    Book updateCost(double cost, String id);

    Book delete(String id);
}
