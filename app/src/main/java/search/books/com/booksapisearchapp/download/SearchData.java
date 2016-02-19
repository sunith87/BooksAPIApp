package search.books.com.booksapisearchapp.download;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snair on 17/02/2016.
 */
public class SearchData implements Serializable {

    private List<SearchItem> items;

    public List<SearchItem> getItems() {
        return items;
    }

}
