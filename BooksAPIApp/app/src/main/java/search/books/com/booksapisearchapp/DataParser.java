package search.books.com.booksapisearchapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snair on 17/02/2016.
 */
public class DataParser {

    public static final String ITEMS = "items";
    public static final String TOTAL_ITEMS = "totalItems";
    public static final String TITLE = "title";


    private final String mRawData;
    private ArrayList<ParsedBook> allBooks = new ArrayList<>();

    public DataParser(String rawData){
        mRawData = rawData;
    }

    public SearchData parse(){

        int totalItems = 0;

        try {
            JSONObject object = new JSONObject(mRawData);
            totalItems = object.getInt(TOTAL_ITEMS);
            JSONArray array = object.getJSONArray(ITEMS);

            for (int i = 0; i<totalItems;i++){
                JSONObject jsonItem = array.getJSONObject(i);
                ParsedBook book  = new ParsedBook(jsonItem);
                allBooks.add(book);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        return new SearchData(allBooks,totalItems);
    }
}
