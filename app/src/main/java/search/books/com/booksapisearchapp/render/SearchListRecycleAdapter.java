package search.books.com.booksapisearchapp.render;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import search.books.com.booksapisearchapp.R;
import search.books.com.booksapisearchapp.model.SearchItem;

/**
 * Created by snair on 17/02/2016.
 */
public class SearchListRecycleAdapter extends RecyclerView.Adapter<SearchListViewHolder> {

    private final List<SearchItem> mAllItems;

    SearchListRecycleAdapter(List<SearchItem> items){
        mAllItems = items;
    }


    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View individualBookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new SearchListViewHolder(individualBookView,parent.getContext());
    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {
            holder.bindViews(mAllItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mAllItems.size();
    }
}
