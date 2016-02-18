package search.books.com.booksapisearchapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.List;

import search.books.com.booksapisearchapp.BookListViewHolder;

/**
 * Created by snair on 17/02/2016.
 */
public class RecycleBookAdapter extends RecyclerView.Adapter<BookListViewHolder> {

    private final List<ParsedBook> mAllBooks;

    RecycleBookAdapter(List<ParsedBook> allBooks){
        mAllBooks = allBooks;
    }


    @Override
    public BookListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View individualBookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new BookListViewHolder(individualBookView);
    }

    @Override
    public void onBindViewHolder(BookListViewHolder holder, int position) {
        try {
            holder.bindViews(mAllBooks.get(position).getTitle());
        } catch (JSONException e) {
            holder.bindViews("");
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mAllBooks.size();
    }
}
