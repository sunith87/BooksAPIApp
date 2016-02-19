package search.books.com.booksapisearchapp.render;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import search.books.com.booksapisearchapp.Constants;
import search.books.com.booksapisearchapp.R;
import search.books.com.booksapisearchapp.model.SearchItem;


/**
 * Created by snair on 17/02/2016.
 */
public class SearchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    private final TextView mTitleView;
    private final ImageView mBookThumbanailView;
    private final Context mContext;
    private SearchItem mSearchItem;

    public SearchListViewHolder(View itemView, Context context) {
        super(itemView);
        mTitleView = (TextView)itemView.findViewById(R.id.bookTitleView);
        mBookThumbanailView = (ImageView)itemView.findViewById(R.id.bookImageView);
        itemView.setOnClickListener(this);

        mContext = context;
    }


    public void bindViews(SearchItem searchItem) {
        mSearchItem = searchItem;
        SearchItem.VolumeInfo volumeInfo = searchItem.volumeInfo;
        String title = volumeInfo.title;
        SearchItem.ImageLinks imageLinks = volumeInfo.imageLinks;
        String url = imageLinks.smallThumbnail;
        mTitleView.setText(title);

        downloadThumbnail(url);
    }

    private void downloadThumbnail(String url) {
        Picasso.with(mContext).
                load(url).
                fit().
                placeholder(R.drawable.placeholder).
                into(mBookThumbanailView);
    }

    @Override
    public void onClick(View v) {
            if (mSearchItem != null){
                Intent searchItemClicked = new Intent();
                searchItemClicked.setAction(Constants.SEARCH_ITEM_CLICKED);
                searchItemClicked.putExtra(Constants.SERACH_ITEM_KEY, mSearchItem);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(searchItemClicked);
            }
    }
}
