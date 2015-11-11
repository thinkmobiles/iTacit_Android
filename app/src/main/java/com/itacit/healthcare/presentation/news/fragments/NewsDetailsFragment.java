package com.itacit.healthcare.presentation.news.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.domain.interactor.news.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.news.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.news.NewsActivity;
import com.itacit.healthcare.presentation.news.mappers.AuthorMapper;
import com.itacit.healthcare.presentation.news.mappers.NewsDetailsMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsDetailsPresenter;
import com.itacit.healthcare.presentation.news.views.INewsDetailsView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by root on 21.10.15.
 */
public class NewsDetailsFragment extends BaseFragmentView<NewsDetailsPresenter, NewsActivity> implements INewsDetailsView, View.OnClickListener {
    @Bind(R.id.iv_headline_FND)     ImageView ivHeadline;
    @Bind(R.id.tv_title_FND)        TextView tvTitle;
    @Bind(R.id.tv_article_FND)      TextView tvArticle;
    @Bind(R.id.tv_category_FND)     TextView tvCategory;
    @Bind(R.id.tv_time_FND)         TextView tvTime;
    @Bind(R.id.iv_icon_IAF)         ImageView ivAuthorIcon;
    @Bind(R.id.tv_name_IAF)         TextView tvAuthorName;
    @Bind(R.id.tv_position_IAF)     TextView tvPosition;

    public static final String NEWS_ID = "newsId";
    private Picasso picasso;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OkHttpClient picassoClient = new OkHttpClient();
        picassoClient.interceptors().add(new AuthInterceptor());
        picasso = new Picasso.Builder(activity)
                .downloader(new OkHttpDownloader(picassoClient))
                .build();
        presenter.loadNewsDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.switchContent(NewsFeedFragment.class, false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, this);

        actionBar.setHomeAsUpIndicator(R.drawable.btn_back);
        activity.setActionBarShadowVisible(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.title_news_feed);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_details;
    }

    @Override
    protected NewsDetailsPresenter createPresenter() {
        long newsId = getArguments().getLong("newsId");
        return new NewsDetailsPresenter(new GetNewsDetailsUseCase((int)newsId), new GetAuthorsUseCase(0, 10), new NewsDetailsMapper(), new AuthorMapper());
    }

    @Override
    public void showNewsDetails(NewsDetailsModel newsDetails) {
        picasso.load(newsDetails.getHeadlineUri()).into(ivHeadline);
        tvTitle.setText(newsDetails.getHeadline());
        tvArticle.setText(Html.fromHtml(newsDetails.getBody()));
        tvCategory.setText(newsDetails.getCategoryName());
        tvTime.setText(newsDetails.getStartDate());
        tvAuthorName.setText(newsDetails.getAuthorName());

//		test
        picasso.load(newsDetails.getHeadlineUri())
                .transform(new CircleTransformation())
                .fit()
                .into(ivAuthorIcon);
    }

    @Override
    public void showAuthorDetails(AuthorModel authorModel) {
//		picasso.load(authorModel.getImageUri())
//				.transform(new CircleTransformation(100, 1))
//				.fit()
//				.into(ivAuthorIcon);
        tvPosition.setText(authorModel.getRole());
    }

    @Override
    public void onClick(View v) {
        activity.switchContent(NewsFeedFragment.class, false);
    }

    public class CircleTransformation implements com.squareup.picasso.Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap bm = source.isMutable() ? source : source.copy(Bitmap.Config.ARGB_8888, true);

            int width = bm.getWidth();
            int height = bm.getHeight();

            Bitmap cropped_bitmap;

            if (width > height) {
                cropped_bitmap = Bitmap.createBitmap(bm,
                        (width / 2) - (height / 2), 0, height, height);
            } else {
                cropped_bitmap = Bitmap.createBitmap(bm, 0, (height / 2)
                        - (width / 2), width, width);
            }

            BitmapShader shader = new BitmapShader(cropped_bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(shader);

            height = cropped_bitmap.getHeight();
            width = cropped_bitmap.getWidth();

            Bitmap output = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(output);
            canvas.drawCircle(width / 2, height / 2, width / 2, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }
}
