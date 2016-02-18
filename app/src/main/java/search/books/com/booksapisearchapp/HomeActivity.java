package search.books.com.booksapisearchapp;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class HomeActivity extends AppCompatActivity implements BookDataDownloader.BookDownloadListener {



    SearchView mSearchView;
    Button mSearchButton;
    ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSearchView = (SearchView)findViewById(R.id.bookSearchView);
        mSearchButton = (Button)findViewById(R.id.btnBookSearch);
        mSearchButton.setOnClickListener(getSearchOnClickListener());
    }


    public View.OnClickListener getSearchOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mSearchView.getQuery().toString();
                if (!TextUtils.isEmpty(query)){
                    fetchBookData(query);
                }else{
                    showSnackBar("Search field cannot be empty");
                }
            }
        };
    }

    private void showSnackBar( String message) {
        View parentLayout = findViewById(R.id.rootView);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void fetchBookData(String query) {
        showProgressBar(query);
        BookDataDownloader downloader = new BookDataDownloader(this);
        downloader.execute(query);
    }

    private void showProgressBar(String query) {
        mProgressBar = new ProgressDialog(this);
        mProgressBar.setMessage("Searching Books API for "+query);
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
        fragmentTransaction.replace(R.id.fragmentContainer, searchListFragment);
        fragmentTransaction.commit();

    }

    private void cleanupProgressBar() {
        mProgressBar.hide();
        mProgressBar = null;
    }

    @Override
    public void onFailure(BookDownloaderError error) {
        cleanupProgressBar();
        showSnackBar("Error while searching: "+error.getError());
    }
}
