package search.books.com.booksapisearchapp.download.image;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * Created by Sunith87 on 17/03/2016.
 */
public class ImageDownloader implements ImageDLAsyncTask.ImageDlListener {

    private static ImageDownloader mImageDownloader;
    public ImageFileManager mFileManager;
    public LruCache<String, Bitmap> mBitmapCache;
    Context mContext;


    private ImageDownloader(Context context) {
        mContext = context;
        mFileManager = new ImageFileManager();
        getBitmapCache();

    }


    public static ImageDownloader getSingleton(Context context){
        if (mImageDownloader == null){
            mImageDownloader = new ImageDownloader(context);
        }
        return mImageDownloader;
    }

    public synchronized void putBitmap(String url, ImageView imageView) {


            Bitmap cachedBitmap = getBitmapCache().get(url);
            if (cachedBitmap != null) {
                imageView.setImageBitmap(cachedBitmap);
                Log.d("TEST", "From Cache....");
            } else {
                Bitmap bitmap = mFileManager.getBitmap(url, imageView);
                if (bitmap != null){
                    setImageAndAddToCache(url, imageView, bitmap);
                    Log.d("TEST", "From file....");
                }else{
                    Log.d("TEST", "From download....");
                    downloadBitmap(url,imageView);

                }

            }


    }

    private void setImageAndAddToCache(String url, ImageView imageView, Bitmap bitmap) {
        getBitmapCache().put(url, bitmap);
        imageView.setImageBitmap(bitmap);
    }

    private void downloadBitmap(String url, ImageView imageView) {
        ImageDLAsyncTask task = new ImageDLAsyncTask(this,imageView);
        task.execute(url);
    }

    public LruCache<String, Bitmap> getBitmapCache() {
        if (mBitmapCache == null) {

            ActivityManager am = (ActivityManager) mContext.getSystemService(
                    Context.ACTIVITY_SERVICE);

            int memorySizeTotal = am.getMemoryClass() * 1024;
            int maxSize = memorySizeTotal / 8;
            mBitmapCache = new LruCache<>(maxSize);
        }
        return mBitmapCache;
    }

    @Override
    public void onDownloaded(String url,Bitmap bitmap, ImageView imageView) {
        setImageAndAddToCache(url, imageView, bitmap);
    }
}
