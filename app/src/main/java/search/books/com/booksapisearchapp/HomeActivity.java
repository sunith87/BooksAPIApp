package search.books.com.booksapisearchapp;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import search.books.com.booksapisearchapp.download.BookDataDownloader;
import search.books.com.booksapisearchapp.download.BookDownloaderError;
import search.books.com.booksapisearchapp.download.SearchData;
import search.books.com.booksapisearchapp.download.SearchItem;
import search.books.com.booksapisearchapp.render.DetailsFragment;
import search.books.com.booksapisearchapp.render.SearchListFragment;

public class HomeActivity extends AppCompatActivity implements BookDataDownloader.BookDownloadListener {


    private static final String SEARCH_RESULT_FRAGMENT_TAG = "SEARCH_RESULT_FRAGMENT_TAG";
    private static final String SEARCH_DETAILS_FRAGMENT_TAG = "SEARCH_DETAILS_FRAGMENT_TAG";


    SearchView mSearchView;
    Button mSearchButton;
    ProgressDialog mProgressBar;
    private SearchView.OnCloseListener searchCloseListener;
    private SearchItemClickReceiver searchItemClickReceiver = new SearchItemClickReceiver();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSearchView = (SearchView)findViewById(R.id.bookSearchView);
        mSearchView.setOnCloseListener(getSearchCloseListener());

        mSearchButton = (Button)findViewById(R.id.btnBookSearch);
        mSearchButton.setOnClickListener(getSearchOnClickListener());

    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter searchClickIntentFilter = new IntentFilter();
        searchClickIntentFilter.addAction(Constants.SEARCH_ITEM_CLICKED);
        LocalBroadcastManager.getInstance(this).registerReceiver(searchItemClickReceiver, searchClickIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(searchItemClickReceiver);
    }

    public View.OnClickListener getSearchOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchQuery();
            }
        };
    }

    private void fetchQuery() {
        String query = mSearchView.getQuery().toString();
        if (!TextUtils.isEmpty(query)){
            fetchBookData(query);
        }else{
            showSnackBar("Search field cannot be empty");
        }
    }

    private void showSnackBar( String message) {
        View parentLayout = findViewById(R.id.rootView);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void fetchBookData(String query) {
        showProgressBar(query);
        BookDataDownloader downloader = new BookDataDownloader(this);
        downloader.execute(query, "40");
    }

    private void showProgressBar(String query) {
        mProgressBar = new ProgressDialog(this);
        mProgressBar.setMessage("Searching Books API for " + query);
        mProgressBar.show();
    }

    @Override
    public void onSuccess(SearchData searchData) {
        cleanupProgressBar();

        SearchListFragment searchListFragment = new SearchListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SearchListFragment.SEARCH_DATA,searchData);
        searchListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, searchListFragment, SEARCH_RESULT_FRAGMENT_TAG);
        fragmentTransaction.commit();

    }

    private void cleanupProgressBar() {
        if (mProgressBar !=null){
            mProgressBar.hide();
        }
        mProgressBar = null;
    }

    @Override
    public void onFailure(BookDownloaderError error) {
        cleanupProgressBar();
        showSnackBar("Error while searching: "+error.getError());
    }

    public SearchView.OnCloseListener getSearchCloseListener() {


        return new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SearchListFragment searchFragment = (SearchListFragment)getFragmentManager().findFragmentByTag(SEARCH_RESULT_FRAGMENT_TAG);
                if (searchFragment != null){
                    getFragmentManager().beginTransaction().
                            remove(searchFragment).
                            commit();
                }


                DetailsFragment detailsFragment = (DetailsFragment)getFragmentManager().findFragmentByTag(SEARCH_DETAILS_FRAGMENT_TAG);
                if (detailsFragment != null){
                    getFragmentManager().beginTransaction().
                            remove(detailsFragment).
                            commit();
                }
                return false;
            }
        };
    }


    private class SearchItemClickReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.SEARCH_ITEM_CLICKED)){
                if (intent.hasExtra(Constants.SERACH_ITEM_KEY)){
                    SearchItem searchItem = (SearchItem) intent.getSerializableExtra(Constants.SERACH_ITEM_KEY);
                    if (searchItem != null){
                        showDetailsFragment(searchItem);
                    }else{
                        showSnackBar("Something went wrong");
                    }

                }
            }
        }
    }

    private void showDetailsFragment(SearchItem searchItem) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundleSearchItem = new Bundle();
        bundleSearchItem.putSerializable(Constants.SEARCH_ITEM_FOR_DETAILS_VIEW,searchItem);

        detailsFragment.setArguments(bundleSearchItem);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, detailsFragment, SEARCH_DETAILS_FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
