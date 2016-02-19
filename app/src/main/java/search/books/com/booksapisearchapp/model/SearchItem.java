package search.books.com.booksapisearchapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snair on 18/02/2016.
 */
public class SearchItem implements Serializable {

    public VolumeInfo volumeInfo;
    public SaleInfo saleInfo;

    public static class VolumeInfo implements Serializable{
        public String title;
        public String subtitle;
        public List<String> authors;
        public String publisher;
        public String publishedDate;
        public String description;
        public ImageLinks imageLinks;
        public String printType;
        public int printCount;
        public String maturityRating;
        public String language;
    }

    public static class ImageLinks implements Serializable {
        public String smallThumbnail;
        public String thumbnail;
    }

    public static class SaleInfo implements Serializable{
        public boolean isEbook;
        public String saleability;
        public String country;
    }


}
