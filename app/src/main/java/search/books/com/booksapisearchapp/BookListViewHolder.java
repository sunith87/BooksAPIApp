package search.books.com.booksapisearchapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by snair on 17/02/2016.
 */
public class BookListViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitleView;
    private final ImageView mBookThumbanailView;

    public BookListViewHolder(View itemView) {
        super(itemView);
        mTitleView = (TextView)itemView.findViewById(R.id.bookTitleView);
        mBookThumbanailView = (ImageView)itemView.findViewById(R.id.bookImageView);
    }

    public void bindViews(String title){
       mTitleView.setText(title);
    }
}
