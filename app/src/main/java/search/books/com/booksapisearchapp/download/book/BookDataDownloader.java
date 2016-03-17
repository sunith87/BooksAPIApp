package search.books.com.booksapisearchapp.download.book;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import search.books.com.booksapisearchapp.model.SearchData;

/**
 * Created by snair on 17/02/2016.
 */
public class BookDataDownloader extends AsyncTask<String,Void,String>{


    public static final int URL = 0;

    public BookDownloadListener mBookDownloadListener;

    public interface BookDownloadListener{
        public void onSuccess(SearchData searchData);
        public void onFailure(BookDownloadError error);
    }


    public BookDataDownloader(BookDownloadListener listener){
        mBookDownloadListener = listener;
    }

    private String download(String endpointUrl)  {
        String result = null;

        try {
            URL url = new URL(endpointUrl);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = convertStreamToString(connection);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = BookDownloadError.MALFORMEDURL;
        } catch (IOException e) {
            e.printStackTrace();
            result = BookDownloadError.IOEXCEPTION;
        }

        return result;
    }



    @Override
    protected String doInBackground(String... params) {
        return download(params[URL]);
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (BookDownloadError.getAllErrors().contains(result)){
            mBookDownloadListener.onFailure(new BookDownloadError(result));
        }else{
            SearchData searchData = null;
            try {
                searchData = getSearchData(result);
            } catch (Exception e) {
                mBookDownloadListener.onFailure(new BookDownloadError(BookError.PARSE_ERROR));
            }
            if (searchData== null || searchData.getItems().size() == 0){
                mBookDownloadListener.onFailure(new BookDownloadError(BookError.PARSE_ERROR));
            }else{
                mBookDownloadListener.onSuccess(searchData);
            }
        }

    }

    private SearchData getSearchData(String result) throws Exception{

        Gson gson = new Gson();
        SearchData searchData = gson.fromJson(result, SearchData.class);

        return searchData;
    }

    @NonNull
    private String convertStreamToString(HttpsURLConnection connection) throws IOException {
        String result;InputStream in = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String read;
        StringBuilder builder = new StringBuilder();

        while ((read = reader.readLine()) != null) {
            builder.append(read);
        }

        reader.close();


        result = builder.toString();
        return result;
    }
}
