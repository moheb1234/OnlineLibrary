package library.dependent_class;

import library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserBookItem {
    private Book book;
    private int number;

    public boolean increaseNumOfBook(int num){
        if (num>0){
            number+=num;
            return true;
        }
        return false;
    }
}
