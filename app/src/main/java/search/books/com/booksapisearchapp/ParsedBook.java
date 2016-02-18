package search.books.com.booksapisearchapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by snair on 17/02/2016.
 */
public class ParsedBook implements Serializable {
    public static final String TITLE = "title";
    public static final String VOLUME_INFO = "volumeInfo";
    public static final String IMAGE_LINKS = "imageLinks";
    public static final String SMALL_THUMBNAIL = "smallThumbnail";
    private final JSONObject mBook;

    public ParsedBook(JSONObject jsonItem) {
        mBook = jsonItem;
    }

    public String getTitle() throws JSONException {
        return mBook.getJSONObject(VOLUME_INFO).getString(TITLE);
    }

    public String getSmallThumbnailUrl() throws JSONException {
        return mBook.getJSONObject(VOLUME_INFO).
                getJSONObject(IMAGE_LINKS).
                getString(SMALL_THUMBNAIL);
    }
}
