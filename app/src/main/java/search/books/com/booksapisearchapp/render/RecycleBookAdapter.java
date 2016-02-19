package search.books.com.booksapisearchapp.render;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import search.books.com.booksapisearchapp.R;
import search.books.com.booksapisearchapp.download.SearchItem;

/**
 * Created by snair on 17/02/2016.
 */
public class RecycleBookAdapter extends RecyclerView.Adapter<BookListViewHolder> {

    private final List<SearchItem> mAllItems;

    RecycleBookAdapter(List<SearchItem> items){
        mAllItems = items;
    }


    @Override
    public BookListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View individualBookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new BookListViewHolder(individualBookView,parent.getContext());
    }

    @Override
    public void onBindViewHolder(BookListViewHolder holder, int position) {
            holder.bindViews(mAllItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mAllItems.size();
    }
}
