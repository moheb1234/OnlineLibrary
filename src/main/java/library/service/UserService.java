package library.service;

import com.sun.jdi.request.DuplicateRequestException;
import library.dependent_class.UserBookItem;
import library.model.User;
import library.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import static library.error_handling.ErrorMessage.*;
import javax.management.InstanceNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public User findById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new InstanceNotFoundException(USER_NOTFOUND);
        }
        return optionalUser.get();
    }

    @Override
    public User signup(User user) {
        userValidation(user);
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicateRequestException(DUPLICATE_USERNAME);
        return userRepository.insert(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @SneakyThrows
    @Override
    public User signIn(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isEmpty())
            throw new LoginException(LOGIN_ERROR);
        return optionalUser.get();
    }

    @Override
    public List<UserBookItem> getBooks(String id) {
        User user = findById(id);
        return user.getBooks();
    }

    @Override
    public User update(User user, String id) {
        User foundUser = findById(id);
        user.setUsername(foundUser.getUsername());
        userValidation(user);
        user.setId(foundUser.getId());
        return userRepository.save(user);
    }

    @Override
    public Integer deposit(String id,int amount) {
        User user = findById(id);
        if (user.getWallet().deposit(amount)){
            userRepository.save(user);
            return user.getWallet().getBalance();
        }
        throw new IllegalStateException(INVALID_NUMBER);
    }

    @Override
    public List<UserBookItem> buyBooks(String userId, List<UserBookItem> books) {
        if (books.size()==0)
            throw new IllegalStateException(EMPTY_BOOK_SELECTED);
        User user = findById(userId);
        if (user.buyBooks(books)){
            userRepository.save(user);
            return books;
        }
        throw new IllegalArgumentException(NOT_ENOUGH_BALANCE);
    }

    @Override
    public User delete(String id) {
        User user = findById(id);
        userRepository.delete(user);
        return user;
    }


    private void userValidation(User user) {
        if (!user.phoneIsValid())
            throw new IllegalArgumentException(INVALID_PHONE_NUMBER);
        if (!user.usernameIsValid())
            throw new IllegalArgumentException(INVALID_USERNAME);
        if (!user.passwordIsValid())
            throw new IllegalArgumentException(INVALID_PASSWORD);
        if (!user.firstnameIsValid())
            throw new IllegalArgumentException(INVALID_FIRSTNAME);
        if (!user.lastnameIsValid())
            throw new IllegalArgumentException(INVALID_LASTNAME);
        if (!user.ageIsValid())
            throw new IllegalArgumentException(INVALID_AGE);
    }
}
