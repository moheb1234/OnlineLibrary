package library.repository;

import library.dependent_class.BookCategories;
import library.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAllByCategories(BookCategories categories);

    List<Book> findAllByAuthor(String author);

    Optional<Book> findByName(String name);
}
