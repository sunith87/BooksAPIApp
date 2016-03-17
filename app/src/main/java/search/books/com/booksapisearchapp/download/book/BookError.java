package search.books.com.booksapisearchapp.download.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by snair on 18/02/2016.
 */
public class BookError {

    public static final String PARSE_ERROR = "PARSE_ERROR";

    private static final ArrayList<String> mErrors = new ArrayList<>();

    private final String mError;


    public BookError(String error, String[] errors ){
        mError = error;
        mErrors.add(PARSE_ERROR);
        mErrors.addAll(Arrays.asList(errors));

    }

    public static List<String> getAllErrors() {
        return mErrors;
    }

    public String getError() {
        return mError;
    }
}
