package search.books.com.booksapisearchapp;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by snair on 17/02/2016.
 */
public class BookDownloaderError {


    public static final String MALFORMEDURL = "MALFORMED_URL_EXCEPTION";
    public static final String IOEXCEPTION = "IO_EXCEPTION";


    private static final String[] mErrors = {MALFORMEDURL,IOEXCEPTION};

    private final String mError;

    public BookDownloaderError(String error){
        mError = error;
    }

    public static List<String> getAllErrors() {
        return Arrays.asList(mErrors);
    }

    public String getError() {
        return mError;
    }
}
