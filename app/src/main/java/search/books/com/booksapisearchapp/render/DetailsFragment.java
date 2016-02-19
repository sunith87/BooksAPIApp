package search.books.com.booksapisearchapp.render;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import search.books.com.booksapisearchapp.Constants;
import search.books.com.booksapisearchapp.R;
import search.books.com.booksapisearchapp.download.SearchItem;

/**
 * Created by snair on 18/02/2016.
 */
public class DetailsFragment extends Fragment {

    public static final String TITLE = "Title: ";
    public static final String DESCRIPTION = "Description: ";
    public static final String AUTHORS = "Authors: ";
    public static final String AUTHOR = "Author: ";
    public static final String MATURITY_RATING = "Maturity Rating: ";
    public static final String LANGUAGE = "Language: ";
    public static final String PUBLISHED_DATE = "Published Date: ";
    public static final String PUBLISHER = "Publisher: ";
    public static final String PRINT_TYPE = "Print type: ";
    public static final String PRINT_COUNT = "Print count: ";
    public static final String SALEABILITY = "Saleability: ";
    public static final String IS_EBOOK = "Is Ebook? ";
    public static final String COUNTRY = "Country: ";
    ImageView mImageView;
    private SearchItem mSearchItem;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mVolumeInfo, mSaleInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.details_fragment, container, false);
        mImageView = (ImageView) inflatedView.findViewById(R.id.details_imageView);
        mToolbar = (Toolbar) inflatedView.findViewById(R.id.DetailsToolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) inflatedView.findViewById(R.id.collapse_toolbar);

        mVolumeInfo = (TextView) inflatedView.findViewById(R.id.volumeInfoDetails);
        mSaleInfo = (TextView) inflatedView.findViewById(R.id.saleInfoDetails);

        return inflatedView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchItem = (SearchItem) getArguments().getSerializable(Constants.SEARCH_ITEM_FOR_DETAILS_VIEW);

        if (mSearchItem != null) {
            Picasso.with(getActivity()).
                    load(mSearchItem.volumeInfo.imageLinks.smallThumbnail).
                    fit().
                    into(mImageView);

            mCollapsingToolbar.setTitle(mSearchItem.volumeInfo.title);
            mCollapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorDarkGray));
            mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.appbar_expanded);
            mCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.appbar_collapsed);

            renderMetaData(mSearchItem);

        }


    }

    private void renderMetaData(SearchItem mSearchItem) {


        SearchItem.VolumeInfo volumeInfo = mSearchItem.volumeInfo;
        renderVolumeInfo(volumeInfo);


        SearchItem.SaleInfo saleInfo = mSearchItem.saleInfo;
        renderSaleInfo(saleInfo);

    }

    private void renderSaleInfo(SearchItem.SaleInfo saleInfo) {
        if (saleInfo != null) {
            StringBuilder saleInfoBuilder = new StringBuilder();
            try {
                setData(saleInfoBuilder, SALEABILITY,  saleInfo.saleability, "<br>");
                setData(saleInfoBuilder, COUNTRY,  saleInfo.country, "<br>");

                saleInfoBuilder.append("<b>");
                saleInfoBuilder.append(IS_EBOOK);
                saleInfoBuilder.append("</b>");
                saleInfoBuilder.append(saleInfo.isEbook);
                saleInfoBuilder.append("<br>");


            } catch (Exception e) {

            }
            mSaleInfo.setText(Html.fromHtml(saleInfoBuilder.toString()));
        }


    }

    private void renderVolumeInfo(SearchItem.VolumeInfo volumeInfo) {
        if (volumeInfo != null) {
            StringBuilder volumeInfoBuilder = new StringBuilder();

            try {


                setData(volumeInfoBuilder, TITLE,  volumeInfo.title, "<br><br>");

                setData(volumeInfoBuilder, DESCRIPTION,  volumeInfo.description, "<br><br>");


                if (volumeInfo.authors.size() > 1) {
                    volumeInfoBuilder.append("<b>");
                    volumeInfoBuilder.append(AUTHORS);
                    volumeInfoBuilder.append("</b>");
                    for (String author : volumeInfo.authors) {
                        volumeInfoBuilder.append(author);
                        volumeInfoBuilder.append(", ");
                    }
                } else {
                    volumeInfoBuilder.append("<b>");
                    volumeInfoBuilder.append(AUTHOR);
                    volumeInfoBuilder.append("</b>");
                    volumeInfoBuilder.append(volumeInfo.authors.get(0));
                }

                volumeInfoBuilder.append("<br>");

                setData(volumeInfoBuilder, MATURITY_RATING, volumeInfo.maturityRating, "<br>");

                setData(volumeInfoBuilder, LANGUAGE,  volumeInfo.language, "<br>");

                setData(volumeInfoBuilder, PUBLISHED_DATE,  volumeInfo.publishedDate, "<br>");

                setData(volumeInfoBuilder, PUBLISHER,  volumeInfo.publisher, "<br>");

                setData(volumeInfoBuilder, PRINT_TYPE,  volumeInfo.printType, "<br>");


                volumeInfoBuilder.append("<b>");
                volumeInfoBuilder.append(PRINT_COUNT);
                volumeInfoBuilder.append("</b>");
                volumeInfoBuilder.append(volumeInfo.printCount);
                volumeInfoBuilder.append("<br>");

            } catch (Exception e) {

            }

            mVolumeInfo.setText(Html.fromHtml(volumeInfoBuilder.toString()));

        }
    }

    private void setData(StringBuilder volumeInfoBuilder, String key, String data, String newLine) {

        if (!TextUtils.isEmpty(data)){
            volumeInfoBuilder.append("<b>");
            volumeInfoBuilder.append(key);
            volumeInfoBuilder.append("</b>");

            volumeInfoBuilder.append(data);
            volumeInfoBuilder.append(newLine);
        }

    }
}
