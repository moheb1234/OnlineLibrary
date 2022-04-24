package library.service;

import library.dependent_class.UserBookItem;
import library.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServiceInterface {
    User findById(String id);

    User signup(User user);

    List<User> findAll();

    User signIn(String username, String password);

    User update(User user, String id);

    User delete(String id);

    List<UserBookItem> getBooks(String id);

    List<UserBookItem> buyBooks(String userId, List<UserBookItem> books);

    Integer deposit(String id,int amount);


}
