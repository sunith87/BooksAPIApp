package search.books.com.booksapisearchapp.download.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Sunith87 on 17/03/2016.
 */
public class ImageFileManager {


    public static final String IMAGES = "images";

    public boolean contains(String url) {
        return false;
    }

    public Bitmap getBitmap(String url, ImageView imageView) {
        int hashcode = url.hashCode();

        File filDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + IMAGES + File.separator
                );

        File foundBitmap = null;

        if (filDir.exists()){
            File[] files = filDir.listFiles();
            for (File file:files){
                if (file.getAbsolutePath().toString().contains(String.valueOf(hashcode))){
                    foundBitmap = file;
                    break;
                }
            }
        }

        if (foundBitmap != null){
            Bitmap bitmap = BitmapFactory.decodeFile(foundBitmap.getAbsolutePath().toLowerCase());
            return bitmap;
        }

        return null;
    }
}
