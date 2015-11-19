package com.itacit.healthcare.presentation.news.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.news.GetNewsUseCase;
import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.news.mappers.NewsMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.NewsFeedView;
import com.itacit.healthcare.presentation.news.views.activity.NewsActivity;
import com.itacit.healthcare.presentation.news.views.adapters.NewsAdapter;
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
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter, NewsActivity> implements NewsFeedView {
	@Bind(R.id.et_search_FNF)				FiltersEditText searchNewsView;
	@Bind(R.id.recycler_view_FNF)			RecyclerView newsRecyclerView;

	private NewsAdapter newsAdapter;
	private ProgressDialog progressDialog;

	@OnClick(R.id.ib_clear_FNF)
	void clearSearch() {
		presenter.clearNewsSearch();
	}

	@Override
	protected void setUpView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		newsRecyclerView.setLayoutManager(layoutManager);
		searchNewsView.setShowMore(true);
	}

	@Override
	protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(true, null);

		activity.setActionBarShadowVisible(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.title_news_feed);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_news_feed;
	}

	@Override
	protected NewsFeedPresenter createPresenter() {
		return new NewsFeedPresenter(new GetNewsUseCase(), new NewsMapper());
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
	public void hideFilters() {
		searchNewsView.setOnTouchListener((v, event) -> false);
		searchNewsView.removeFilters();
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
	public void showNewsItemDetails(String newsId) {
		Bundle args = new Bundle(1);
		args.putString(NewsDetailsFragment.NEWS_ID, newsId);
		activity.switchContent(NewsDetailsFragment.class, true, args);
	}

    @Override
    public Observable<String> getNewsSearchTextObs() {
        return RxTextView.textChangeEvents(searchNewsView).map(e -> searchNewsView.getInputText());
    }

    @Override
    public BehaviorSubject<NewsSearch> getNewsSearch() {
        return activity.getNewsSearchSubj();
    }
}
