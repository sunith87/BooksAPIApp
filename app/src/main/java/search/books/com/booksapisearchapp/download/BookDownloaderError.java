package search.books.com.booksapisearchapp.download;

/**
 * Created by snair on 17/02/2016.
 */
public class BookDownloaderError extends BookError {


    public static final String MALFORMEDURL = "MALFORMED_URL_EXCEPTION";
    public static final String IOEXCEPTION = "IO_EXCEPTION";

    private static final String[] mErrors = {MALFORMEDURL,IOEXCEPTION};


    public BookDownloaderError(String error){
        super(error,mErrors);
    }


}
