package com.itacit.healthcare.presentation.news.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetNewsUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.news.NewsActivity;
import com.itacit.healthcare.presentation.news.adapters.NewsAdapter;
import com.itacit.healthcare.presentation.news.mappers.NewsMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter, NewsActivity> implements INewsFeedView {
	@Bind(R.id.et_search_FN)				FiltersEditText searchNewsView;
	@Bind(R.id.recycler_view_FN)			RecyclerView newsRecyclerView;

	private NewsAdapter newsAdapter;
	private ProgressDialog progressDialog;

	@OnClick(R.id.ib_clear_FN)
	void clearSearch() {
		searchNewsView.setOnTouchListener((v, event) -> false);
		searchNewsView.removeFilters();
		presenter.loadNews();
	}

	@Override
	protected void setUpView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		newsRecyclerView.setLayoutManager(layoutManager);
		searchNewsView.setShowMore(true);
	}

	@Override
	protected void setUpActionBar(ActionBar actionBar) {

        switchToolbarIndicator(true);

		activity.setActionBarShadowVisible(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.title_news_feed);
	}

    private void switchToolbarIndicator(boolean enable) {
        toggle.setDrawerIndicatorEnabled(enable);
    }

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_news_feed;
	}

	@Override
	protected NewsFeedPresenter createPresenter() {
		return new NewsFeedPresenter(new GetNewsUseCase(0, 100), new NewsMapper());
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
		searchNewsView.post(() -> {
			// disable touch
            searchNewsView.setOnTouchListener((v, event) -> true);

            for (Filter filter : filters) {
                searchNewsView.addFilter(filter, false);
            }
            searchNewsView.scrollTo(0,0);
        });
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
        return activity.getSearchNews();
    }
}
