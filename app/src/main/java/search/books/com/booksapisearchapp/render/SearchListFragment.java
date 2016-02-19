package search.books.com.booksapisearchapp.render;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import search.books.com.booksapisearchapp.R;
import search.books.com.booksapisearchapp.model.SearchData;

/**
 * Created by snair on 17/02/2016.
 */
public class SearchListFragment extends Fragment {

    public static final String SEARCH_DATA = "search_data";

    private RecyclerView mRecyclerListView;
    private GridLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.list_fragment, container, false);
        mRecyclerListView = (RecyclerView)inflatedView.findViewById(R.id.bookListRecycler);
        return inflatedView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SearchData searchData = (SearchData)getArguments().getSerializable(SEARCH_DATA);
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerListView.setLayoutManager(mLayoutManager);
        mRecyclerListView.setAdapter(new SearchListRecycleAdapter(searchData.getItems()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
