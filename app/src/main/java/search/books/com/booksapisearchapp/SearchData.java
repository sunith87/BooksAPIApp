package search.books.com.booksapisearchapp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snair on 17/02/2016.
 */
public class SearchData implements Serializable {

    private List<ParsedBook> allBooks;
    private int numberOfItems;

    public SearchData( List<ParsedBook> books, int numberOfItems){
        this.allBooks = books;
        this.numberOfItems = numberOfItems;
    }

    public List<ParsedBook> getAllBooks() {
        return allBooks;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }
}
