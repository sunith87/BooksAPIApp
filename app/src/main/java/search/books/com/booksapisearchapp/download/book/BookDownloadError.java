package search.books.com.booksapisearchapp.download.book;

/**
 * Created by snair on 17/02/2016.
 */
public class BookDownloadError extends BookError {


    public static final String MALFORMEDURL = "MALFORMED_URL_EXCEPTION";
    public static final String IOEXCEPTION = "IO_EXCEPTION";

    private static final String[] mErrors = {MALFORMEDURL,IOEXCEPTION};


    public BookDownloadError(String error){
        super(error,mErrors);
    }


}
