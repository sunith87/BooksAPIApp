package search.books.com.booksapisearchapp.download;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by snair on 17/02/2016.
 */
public class BookDataDownloader extends AsyncTask<String,Void,String>{

    public final static String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=search+%s&filter=full&maxResults=%s";
    public static final int QUERY_POSITION = 0;
    public static final int NUMBER_OF_BOOKS = 1;

    public BookDownloadListener mBookDownloadListener;



    public interface BookDownloadListener{
        public void onSuccess(SearchData searchData);
        public void onFailure(BookDownloaderError error);
    }


    public BookDataDownloader(BookDownloadListener listener){
        mBookDownloadListener = listener;
    }

    private String download(String query, String numberOfResults)  {
        String result = null;

        String format = String.format(GOOGLE_API_URL, query.replaceAll(" ","+"),numberOfResults);
        try {
            URL url = new URL(format);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();


            if (connection.getResponseCode()== HttpURLConnection.HTTP_OK){
                InputStream in = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String read;
                StringBuilder builder = new StringBuilder();

                while ((read = reader.readLine()) !=null){
                    builder.append(read);
                }

                reader.close();


                 result = builder.toString();
                Log.d("Test", result);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = BookDownloaderError.MALFORMEDURL;
        } catch (IOException e) {
            e.printStackTrace();
            result = BookDownloaderError.IOEXCEPTION;
        }

        return result;
    }

    @Override
    protected String doInBackground(String... params) {
        return download(params[QUERY_POSITION],params[NUMBER_OF_BOOKS]);
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (BookDownloaderError.getAllErrors().contains(result)){
            mBookDownloadListener.onFailure(new BookDownloaderError(result));
        }else{
            SearchData searchData = getSearchData(result);
            if (searchData.getItems().size() == 0){
                mBookDownloadListener.onFailure(new BookDownloaderError(BookError.PARSE_ERROR));
            }else{
                mBookDownloadListener.onSuccess(searchData);
            }
        }

    }

    private SearchData getSearchData(String result) {

        Gson gson = new Gson();
        SearchData searchData = gson.fromJson(result, SearchData.class);

        return searchData;
    }
}
