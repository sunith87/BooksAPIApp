package search.books.com.booksapisearchapp.download.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sunith87 on 17/03/2016.
 */
public class ImageDLAsyncTask extends AsyncTask<String, Void, Bitmap> {


    private final ImageView mImageView;
    private ImageDlListener mDL_Listener;
    private String mUrl;

    public interface ImageDlListener {
        void onDownloaded(String url,Bitmap bitmap, ImageView imageView);
    }

    public ImageDLAsyncTask(ImageDlListener listener, ImageView imageView){
        mDL_Listener = listener;
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        mUrl = params[0];
        Bitmap bitmap = null;

        try {
            URL fullUrl = new URL(mUrl);
            fullUrl.getFile();
            URLConnection connection = fullUrl.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileDir = extStorageDirectory + File.separator
                    + ImageFileManager.IMAGES;
            File fileDirFile = new File(fileDir);
            if (!fileDirFile.exists()){
                fileDirFile.mkdir();
            }

            File file = new File(fileDir, getFileName(mUrl) + ".png");



            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap !=null && mDL_Listener != null){
            mDL_Listener.onDownloaded(mUrl,bitmap,mImageView);
        }
    }

    private int getFileName(String url) {
        String fileName = Uri.parse(url).getLastPathSegment();
        return url.hashCode();
    }
}
