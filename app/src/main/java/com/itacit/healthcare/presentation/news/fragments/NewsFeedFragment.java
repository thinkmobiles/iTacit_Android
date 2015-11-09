package com.itacit.healthcare.presentation.news.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetNewsUseCase;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.news.NewsActivity;
import com.itacit.healthcare.presentation.news.adapters.NewsAdapter;
import com.itacit.healthcare.presentation.news.adapters.NewsSearchAdapter;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter> implements INewsFeedView {
	@Bind(R.id.et_search_FN)				FiltersEditText searchNewsView;
	@Bind(R.id.recycler_view_FN)			RecyclerView newsRecyclerView;

	private NewsAdapter newsAdapter;
	private ProgressDialog progressDialog;

	@OnClick(R.id.ib_clear_FN)
	void clearSearch() {
		searchNewsView.removeFilters();
		presenter.loadNews();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presenter.loadNews();
	}

	@Override
	protected void setUpView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		newsRecyclerView.setLayoutManager(layoutManager);
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
		return R.layout.fragment_news_feed;
	}

	@Override
	protected NewsFeedPresenter createPresenter() {
		return new NewsFeedPresenter(new GetNewsUseCase(0, 100), new NewsModelMapper());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_filter, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.filter:
				activity.switchContent(NewsSearchFragment.class, true);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void showNews(List<NewsModel> news) {
		newsAdapter = new NewsAdapter(getActivity(), news);
		newsRecyclerView.setAdapter(newsAdapter);
		newsAdapter.setOnNewsItemSelectedListener(this::showNewsItemDetails);

		ArrayList<String> headers = new ArrayList<>(news.size());
		for(NewsModel newsModel : news) {
			headers.add(newsModel.getHeadline());
		}

		searchNewsView.setAdapter(new ArrayAdapter<>(activity, R.layout.list_item_search_news, headers));
	}

    @Override
    public void showFilters(List<Filter> filters) {
        for (Filter filter : filters) {
            searchNewsView.addFilter(filter);
        }
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
        }
        progressDialog.show();
    }

	@Override
	public void hideProgress() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.hide();
		}
	}

	@Override
	public void showNewsItemDetails(long newsId) {
		Bundle args = new Bundle(1);
		args.putLong(NewsDetailsFragment.NEWS_ID, newsId);
		activity.switchContent(NewsDetailsFragment.class, true, args);
	}

    @Override
    public Observable<String> getNewsSearchTextObs() {
        return RxTextView.textChangeEvents(searchNewsView).map(e -> searchNewsView.getInputText());
    }

    @Override
    public BehaviorSubject<NewsSearch> getNewsSearch() {
        return ((NewsActivity)activity).getSearchNews();
    }
}
