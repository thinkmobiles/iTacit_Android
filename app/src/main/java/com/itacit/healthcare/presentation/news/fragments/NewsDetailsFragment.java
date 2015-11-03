package com.itacit.healthcare.presentation.news.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.domain.interactor.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.mapper.NewsDetailsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsDetailsPresenter;
import com.itacit.healthcare.presentation.news.views.INewsDetailsView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 21.10.15.
 */
public class NewsDetailsFragment extends BaseFragmentView<NewsDetailsPresenter> implements INewsDetailsView {
	public static final String NEWS_ID = "newsId";
	@Bind(R.id.iv_headline_FND)
	ImageView ivHeadline;
	@Bind(R.id.tv_title_FND)
	TextView tvTitle;
	@Bind(R.id.tv_article_FND)
	TextView tvArticle;
	@Bind(R.id.tv_category_FND)
	TextView tvCategory;
	@Bind(R.id.tv_time_FND)
	TextView tvTime;
	@Bind(R.id.iv_icon_IAF)
	ImageView ivAuthorIcon;
	@Bind(R.id.tv_name_IAF)
	TextView tvAuthorName;
	@Bind(R.id.tv_position_IAF)
	TextView tvPosition;

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
	protected void setUpView() {

	}

	@Override
	protected void setUpActionBar(ActionBar actionBar) {
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
		return new NewsDetailsPresenter(new GetNewsDetailsUseCase(newsId), new NewsDetailsModelMapper());
	}

	@Override
	public void showNewsDetails(NewsDetailsModel newsDetails) {
		picasso.load(newsDetails.getHeadlineUri()).into(ivHeadline);
		tvTitle.setText(newsDetails.getHeadline());
		tvArticle.setText(Html.fromHtml(newsDetails.getBody()));
		tvCategory.setText(newsDetails.getCategoryName());
		tvTime.setText(newsDetails.getStartDate());
		tvAuthorName.setText(newsDetails.getAuthorName());
	}
}
