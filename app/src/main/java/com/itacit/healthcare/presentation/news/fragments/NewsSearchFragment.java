package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.news.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.news.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.WrapChildsLayotManager;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.base.widgets.datePicker.DatePickerFragment;
import com.itacit.healthcare.presentation.news.NewsActivity;
import com.itacit.healthcare.presentation.news.adapters.AuthorsAdapter;
import com.itacit.healthcare.presentation.news.adapters.CategoriesAdapter;
import com.itacit.healthcare.presentation.news.mappers.AuthorMapper;
import com.itacit.healthcare.presentation.news.mappers.CategoryMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.news.presenters.INewsSearchPresenter.DateType;
import com.itacit.healthcare.presentation.news.presenters.NewsSearchPresenter;
import com.itacit.healthcare.presentation.news.views.INewsSearchView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public class NewsSearchFragment extends BaseFragmentView<NewsSearchPresenter, NewsActivity> implements INewsSearchView,
        AuthorsAdapter.OnAuthorsItemSelectedListener, CategoriesAdapter.OnCategoriesItemSelectedListener, View.OnClickListener {
    @Bind(R.id.sv_root_FNS)                     ScrollView rootSv;
    @Bind(R.id.et_serch_FNS)                    FiltersEditText searchFiltersEt;
    @Bind(R.id.tv_count_author_FNS)             TextView tvCountAuthor;
    @Bind(R.id.iv_expand_author_FNS)            ImageView ivExpandAuthor;
    @Bind(R.id.recycler_view_authors_FNS)       RecyclerView authorsRv;
    @Bind(R.id.tv_count_category_FNS)           TextView tvCountCategory;
    @Bind(R.id.iv_expand_category_FNS)          ImageView ivExpandCategory;
    @Bind(R.id.recycler_view_categories_FNS)    RecyclerView categoriesRv;
    @Bind(R.id.tv_from_FNS)                     Button tvDateFrom;
    @Bind(R.id.tv_to_FNS)                       Button tvDateTo;

	private AuthorsAdapter authorsAdapter;
	private CategoriesAdapter categoriesAdapter;


    @OnClick(R.id.ib_clear_FNS)
    void onClearFilters() {
        searchFiltersEt.removeFilters();
    }

    @OnClick({R.id.tv_from_FNS, R.id.tv_to_FNS})
    void showDatePicker(View view) {
        switch (view.getId()) {
            case R.id.tv_from_FNS:
                DatePickerFragment fromDatePicker =
                        new DatePickerFragment((datePicker, year, monthOfYear, dayOfMonth) ->
                                presenter.onDateSelected(DateType.From, year, monthOfYear, dayOfMonth),
                                () -> presenter.onDateClear(DateType.From));
                fromDatePicker.show(getFragmentManager(), "FromDatePicker");
                break;
            case R.id.tv_to_FNS:
                DatePickerFragment toDatePicker =
                        new DatePickerFragment((datePicker, year, monthOfYear, dayOfMonth) ->
                                presenter.onDateSelected(DateType.To, year, monthOfYear, dayOfMonth),
                                () -> presenter.onDateClear(DateType.To));
                toDatePicker.show(getFragmentManager(), "ToDatePicker");
                break;
        }
    }

    @OnClick(R.id.btn_search_FNS)
    void searchNews() {
        if (presenter.isDateValid()) {
            NewsSearch search = presenter.getNewsSearch();
            activity.getSearchNews().onNext(search);
            activity.switchContent(NewsFeedFragment.class, false);
        }
    }

    @OnClick({R.id.iv_expand_author_FNS, R.id.iv_expand_category_FNS})
    void onExpandClick(View view) {
        switch (view.getId()) {
            case R.id.iv_expand_author_FNS:
                toggleListVisibility(authorsRv, ivExpandAuthor);
                break;
            case R.id.iv_expand_category_FNS:
                toggleListVisibility(categoriesRv, ivExpandCategory);
                break;
        }
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
        searchFiltersEt.setShowMore(false);
        preventRootScroll(authorsRv);
        preventRootScroll(categoriesRv);
        preventRootScroll(searchFiltersEt);
        authorsRv.setLayoutManager(new WrapChildsLayotManager(activity));
        categoriesRv.setLayoutManager(new WrapChildsLayotManager(activity));
    }

    private void preventRootScroll(View view) {
        view.setOnTouchListener((v, event) -> {
            rootSv.requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    rootSv.requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, this);

        actionBar.setHomeAsUpIndicator(R.drawable.btn_back);
        activity.setActionBarShadowVisible(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.title_news_filter);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_search;
    }

    @Override
    protected NewsSearchPresenter createPresenter() {
        return new NewsSearchPresenter(new GetAuthorsUseCase(0, 10), new GetCategoriesUseCase(0, 10), new AuthorMapper(), new CategoryMapper());
    }

    @Override
    public void showInvalidDateWarning() {
        Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.invalid_date_interval), Toast.LENGTH_LONG).show();
    }

	@Override
	public void showSelectDateWarning() {
		Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.select_date_interval), Toast.LENGTH_LONG).show();
	}

	@Override
	public void unselectAuthor(long id) {
		authorsAdapter.getSelectedAuthorsIds().remove(id);
		authorsAdapter.notifyDataSetChanged();
	}

	@Override
	public void unselectCategory(long id) {
        categoriesAdapter.getSelectedCategoriesIds().remove(id);
		categoriesAdapter.notifyDataSetChanged();
	}

	@Override
    public Observable<String> getSearchTextObs() {
        return RxTextView.textChangeEvents(searchFiltersEt).map(e -> searchFiltersEt.getInputText());
    }

	@Override
	public Observable<Filter> getFilterRemovedObs() {
		return searchFiltersEt.getChipRemovedSubject();
	}

    @Override
    public List<Filter> getFilters() {
        return searchFiltersEt.getSelectedFilters();
    }

    private void toggleListVisibility(RecyclerView recyclerView, ImageView expandIv) {

        if (recyclerView.getVisibility() == View.GONE) {

	        Animation animShow = AnimationUtils.loadAnimation(getActivity(),
			        R.anim.anim_show);
	        animShow.setAnimationListener(new Animation.AnimationListener() {
		        @Override
		        public void onAnimationStart(Animation animation) {

			        expandIv.setImageResource(R.drawable.ic_drop);
		        }

		        @Override
		        public void onAnimationEnd(Animation animation) {

		        }

		        @Override
		        public void onAnimationRepeat(Animation animation) {

		        }
	        });
	        recyclerView.setVisibility(View.VISIBLE);
	        recyclerView.startAnimation(animShow);

        } else {
	        Animation animHide = AnimationUtils.loadAnimation(getActivity(),
			        R.anim.anim_hide);
	        animHide.setAnimationListener(new Animation.AnimationListener() {
		        @Override
		        public void onAnimationStart(Animation animation) {
			        expandIv.setImageResource(R.drawable.ic_drop_hide);
		        }

		        @Override
		        public void onAnimationEnd(Animation animation) {

			        recyclerView.setVisibility(View.GONE);

		        }

		        @Override
		        public void onAnimationRepeat(Animation animation) {

		        }
	        });

	        recyclerView.startAnimation(animHide);

        }
    }

    private Button selectDateView(DateType dateType) {

	    Button btn = null;
        switch (dateType) {
            case From:  btn = tvDateFrom;
                 break;
            case To:  btn = tvDateTo;
	             break;
        }
	    return btn;
    }

    @Override
    public void showDate(DateType dateType, String date) {
        Button btn = selectDateView(dateType);
        btn.setText(date);
        btn.setTextColor(getResources().getColor(android.R.color.white));
        btn.setBackgroundResource(R.drawable.bg_btn_date_pressed);
        btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_act, 0, 0, 0);
    }

    @Override
    public void resetDate(DateType dateType) {
        Button btn = selectDateView(dateType);
        btn.setText(R.string.add_date);
        btn.setTextColor(getResources().getColor(R.color.gray_dark));
        btn.setBackgroundResource(R.drawable.bg_btn_date);
        btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar, 0, 0, 0);
	    btn.invalidate();
    }

    @Override
    public void showAuthors(List<AuthorModel> authors) {
        authorsAdapter = new AuthorsAdapter(getActivity(), authors);
        for (Filter filter : searchFiltersEt.getSelectedFilters()) {
            authorsAdapter.getSelectedAuthorsIds().add(filter.getId());
        }
        authorsRv.setAdapter(authorsAdapter);
        authorsAdapter.setOnAuthorsItemSelectedListener(this);
        tvCountAuthor.setText(String.valueOf(authors.size()));
    }

    @Override
    public void showCategories(List<CategoryModel> categories) {
        categoriesAdapter = new CategoriesAdapter(getActivity(), categories);
        for (Filter filter : searchFiltersEt.getSelectedFilters()) {
            categoriesAdapter.getSelectedCategoriesIds().add(filter.getId());
        }
        categoriesRv.setAdapter(categoriesAdapter);
        categoriesAdapter.setOnCategoriesItemSelectedListener(this);
        tvCountCategory.setText(String.valueOf(categories.size()));
    }

    @Override
    public void addFilter(Filter filter) {
        searchFiltersEt.addFilter(filter, true);
    }

    @Override
    public void removeFilter(Filter filter) {
        searchFiltersEt.removeFilter(filter);
    }

    @Override
    public void onAuthorsSelected(long authorId) {
        presenter.selectAuthorFilterById(authorId);
    }

    @Override
    public void onAuthorsDeselected(long authorId) {
        presenter.unselectAuthorFilterById(authorId);
    }

    @Override
    public void onCategoriesSelected(long categoryId) {
        presenter.selectCategoryFilterById(categoryId);
    }

    @Override
    public void onCategoriesDeselected(long categoryId) {
        presenter.unselectCategoryFilterById(categoryId);
    }

    @Override
    public void onClick(View v) {
        activity.switchContent(NewsFeedFragment.class, false);
    }
}
