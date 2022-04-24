package library;

import com.sun.jdi.request.DuplicateRequestException;
import library.dependent_class.Address;
import library.dependent_class.UserBookItem;
import library.dependent_class.Wallet;
import library.model.Book;
import library.model.User;
import library.repository.UserRepository;
import library.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.management.InstanceNotFoundException;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;


    @Test
    void whenFindNotExistUserThenThrowException() {
        Assertions.assertThrows(InstanceNotFoundException.class, () -> userService.findById("id"));
    }

    @Test
    void whenFindExistUserThenReturnAnUser() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(new User()));
        Assertions.assertNotNull(userService.findById(anyString()));
    }

    @Test
    void whenLoginWithWrongPasswordThenThrowLoginException() {
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(LoginException.class, () -> userService.signIn("username", "pas"));
    }

    @Test
    void whenLoginWithWrongUsernameThenThrowLoginException() {
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(LoginException.class, () -> userService.signIn("use", "password"));
    }

    @Test
    void whenLoginWithInfoValidThenReturnNotNull() {
        when(userRepository.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(new User()));
        Assertions.assertNotNull(userService.signIn("username", "password"));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenUsernameContainsSpecialChar() {
        User user = new User("moheb", "moallem", "jon12@3", "Jon123456@#", "09114099564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenPasswordLengthSmallerThan10() {
        User user = new User("moheb", "moallem", "jon12", "jon12#4", "09114099564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenPasswordNotContainsSpecialChar() {
        User user = new User("moheb", "moallem", "jon12", "Jon123456346f", "09114099564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenFirstnameIsInvalid() {
        User user = new User("moheb3", "moallem", "jon12", "Jon123456@#", "09114099564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenLastnameIsInvalid() {
        User user = new User("moheb", "moallem11", "jon12", "Jon123456@#", "09114099564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenPhoneNumberLengthIsNotEqualWith11() {
        User user = new User("moheb", "moallem", "jon12", "Jon123456@#", "0911409564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowIllegalArgumentExceptionWhenPhoneNumberNotStartWith09() {
        User user = new User("moheb", "moallem", "jon12", "Jon123456@#", "02114099564", new Address(), 26);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signup(user));
    }

    @Test
    void inSignupExpectedToThrowDuplicateExceptionWhenUsernameIsAlreadyExist() {
        User user = new User("moheb", "moallem", "jon12", "Jon123456@#", "09114099564", new Address(), 26);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(DuplicateRequestException.class, () -> userService.signup(user));
    }


    @Test
    void inSignupExpectedToReturnAnUserWhenAllParamIsValid() {
        User user = new User("moheb", "moallem", "jon12", "Jon123456@#", "09114099564", new Address(), 26);
        when(userRepository.insert(user)).thenReturn(user);
        Assertions.assertEquals(user, userService.signup(user));
    }

    @Test
    void inDepositExpectedToThrowIllegalStateWhenAmountIsMines() {
        User user = new User();
        user.setWallet(new Wallet(0));
        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        Assertions.assertThrows(IllegalStateException.class, () -> userService.deposit("id", -100));
    }

    @Test
    void inDepositExpectedToBalanceBe2000() {
        User user = new User();
        user.setWallet(new Wallet(0));
        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        userService.deposit("id", 2000);
        Assertions.assertEquals(2000, user.getWallet().getBalance());
    }

    @Test
    void inBuyBookExpectedThrowIllegalStateExceptionWhenBooksIsEmpty() {
        when(userRepository.findById("id")).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(IllegalStateException.class, () -> userService.buyBooks("id", new ArrayList<>()));
    }

    @Test
    void inBuyBookExpectedThrowIllegalArgumentExceptionWhenBalanceIsNotEnough() {
        List<UserBookItem> bookItems = List.of(new UserBookItem(new Book(300),2),new UserBookItem(new Book(400),3));
        User user = new User();
        user.setWallet(new Wallet(1799));
        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.buyBooks("id",bookItems));
    }

    @Test
    void SuccessfullyBuyBook() {
        List<UserBookItem> bookItems = List.of(new UserBookItem(new Book(300),2),new UserBookItem(new Book(400),3));
        User user = new User(new Wallet(2000));
        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        userService.buyBooks("id",bookItems);
        Assertions.assertEquals(user.getTransactions().size(),1);
        Assertions.assertEquals(bookItems.get(1),user.getBooks().get(1));
        Assertions.assertEquals(bookItems.get(0),user.getBooks().get(0));
        Assertions.assertEquals(user.getWallet().getBalance(),200);
    }
}
