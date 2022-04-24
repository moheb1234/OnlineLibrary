package library.model;

import library.dependent_class.BookCategories;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "book")
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    private String name, author;
    private BookCategories categories;
    private int printYear;
    private int stock;
    private double cost;

    public Book(String name, String author, BookCategories categories, int printYear, double cost , int stock) {
        this.name = name;
        this.author = author;
        this.categories = categories;
        this.printYear = printYear;
        this.cost = cost;
        this.stock = stock;
    }

    public Book(double cost) {
        this.cost = cost;
    }

    public Book(int stock, double cost) {
        this.stock = stock;
        this.cost = cost;
    }

    public boolean add(int number){
        if (number>0){
            stock+=number;
            return true;
        }
        return false;
    }

    public boolean updateCost(double newCost){
        if (newCost>0){
            cost = newCost;
            return true;
        }
        return false;
    }
}
