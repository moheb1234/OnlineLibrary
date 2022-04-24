package library.controller;

import library.model.Admin;
import library.model.Book;
import library.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("admin/signIn")
    public ResponseEntity<Admin> signIn(@RequestParam String username, @RequestParam String password) {
        return new ResponseEntity<>(adminService.SignIn(username, password), HttpStatus.OK);
    }

    @PostMapping("admin/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        return new ResponseEntity<>(adminService.createBook(book),HttpStatus.CREATED);
    }

    @PutMapping("admin/add-book/{bookId}")
    public ResponseEntity<Book> addBook(@PathVariable String bookId,@RequestParam int number){
        return new ResponseEntity<>(adminService.addBook(bookId, number),HttpStatus.OK);
    }

    @PutMapping("admin/update-cost/{bookId}")
    public ResponseEntity<Book> updateBookCost(@PathVariable String bookId,@RequestParam double cost){
        return new ResponseEntity<>(adminService.updateBookCost(bookId, cost),HttpStatus.OK);
    }

    @DeleteMapping("admin/delete-book/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable String bookId){
        return new ResponseEntity<>(adminService.deleteBook(bookId),HttpStatus.OK);
    }

}
