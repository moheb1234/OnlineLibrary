package library.controller;

import library.dependent_class.UserBookItem;
import library.model.User;
import library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return new ResponseEntity<>(userService.signup(user), HttpStatus.OK);
    }

    @GetMapping("user/signIn")
    public ResponseEntity<User> signIn(@RequestParam String username, @RequestParam String password) {
        return new ResponseEntity<>(userService.signIn(username, password),HttpStatus.OK);
    }

    @GetMapping("user/books/{id}")
    public ResponseEntity<List<UserBookItem>> getBooks(@PathVariable String id){
        return new ResponseEntity<>(userService.getBooks(id),HttpStatus.OK);
    }

    @PutMapping("user/update/{id}")
    public ResponseEntity<User> updateInfo(@RequestBody User user, @PathVariable String id){
        return new ResponseEntity<>(userService.update(user,id),HttpStatus.OK);
    }

    @PutMapping("user/deposit/{id}")
    public ResponseEntity<Integer> deposit(@PathVariable String id,@RequestParam int amount){
        return new ResponseEntity<>(userService.deposit(id, amount),HttpStatus.OK);

    }

    @PutMapping("user/buy-book/{id}")
    public ResponseEntity<List<UserBookItem>> buyBook(@PathVariable String id,@RequestBody List<UserBookItem> books){
        return new ResponseEntity<>(userService.buyBooks(id, books),HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<User> delete(@PathVariable String id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }
}
