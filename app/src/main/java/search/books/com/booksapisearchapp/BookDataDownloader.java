package search.books.com.booksapisearchapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by snair on 17/02/2016.
 */
public class BookDataDownloader extends AsyncTask<String,Void,String>{

    public final static String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=search+%s";
    public static final int QUERY_POSITION = 0;

    public BookDownloadListener mBookDownloadListener;



    public interface BookDownloadListener{
        public void onSuccess(SearchData searchData);
        public void onFailure(BookDownloaderError error);
    }


    public BookDataDownloader(BookDownloadListener listener){
        mBookDownloadListener = listener;
    }

    private String download(String query)  {
        String result = null;

        String format = String.format(GOOGLE_API_URL, query);
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
        return download(params[QUERY_POSITION]);
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (BookDownloaderError.getAllErrors().contains(result)){
            mBookDownloadListener.onFailure(new BookDownloaderError(result));
        }else{
            mBookDownloadListener.onSuccess(getSearchData(result));
        }

    }

    private SearchData getSearchData(String result) {
        DataParser dataParser = new DataParser(result);
        return dataParser.parse();
    }
}
