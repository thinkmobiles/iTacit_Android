package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.WrapChildsLayotManager;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.base.widgets.datePicker.DatePickerFragment;
import com.itacit.healthcare.presentation.base.widgets.wheelDatePicker.WheelDatePicker;
import com.itacit.healthcare.presentation.news.adapters.AuthorsAdapter;
import com.itacit.healthcare.presentation.news.adapters.CategoriesAdapter;
import com.itacit.healthcare.presentation.news.mapper.AuthorModelMapper;
import com.itacit.healthcare.presentation.news.mapper.CategoryModelMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;
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
public class NewsSearchFragment extends BaseFragmentView<NewsSearchPresenter> implements INewsSearchView {
    @Bind(R.id.sv_root_FNS)
    ScrollView rootSv;
    @Bind(R.id.et_serch_FNS)
    FiltersEditText searchFiltersEt;
    @Bind(R.id.tv_count_author_FNS)
    TextView tvCountAuthor;
    @Bind(R.id.iv_expand_author_FNS)
    ImageView ivExpandAuthor;
    @Bind(R.id.recycler_view_authors_FNS)
    RecyclerView authorsRv;
    @Bind(R.id.tv_count_category_FNS)
    TextView tvCountCategory;
    @Bind(R.id.iv_expand_category_FNS)
    ImageView ivExpandCategory;
    @Bind(R.id.recycler_view_categories_FNS)
    RecyclerView categoriesRv;
    @Bind(R.id.tv_from_FNS)
    TextView tvDateFrom;
    @Bind(R.id.tv_to_FNS)
    TextView tvDateTo;
    @Bind(R.id.datepicker_wheel_FNS)
    WheelDatePicker datePickerWheel;
    @Bind(R.id.tv_done_FNS)
    TextView tvDone;
    @Bind(R.id.tv_cancel_FNS)
    TextView tvCancel;
    @Bind(R.id.btn_search_FNS)
    Button btnSearch;


    @OnClick(R.id.ib_clear_FNS)
    void onClearFilters() {
        searchFiltersEt.removeFilters();
    }

    private AuthorsAdapter authorsAdapter;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void setUpView() {
        preventRootScroll(authorsRv);
        preventRootScroll(categoriesRv);
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
        return new NewsSearchPresenter(new GetAuthorsUseCase(0, 10),new GetCategoriesUseCase(0, 10), new AuthorModelMapper(), new CategoryModelMapper());
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

    @Override
    public Observable<String> getSearchTextObs() {
        return RxTextView.textChangeEvents(searchFiltersEt).map(e -> searchFiltersEt.getInputText());
    }

    @Override
    public Observable<Integer> getListClickObs() {
        return null;
    }

    @OnClick({R.id.iv_expand_author_FNS,R.id.iv_expand_category_FNS})
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

    private void toggleListVisibility(RecyclerView recyclerView, ImageView expandIv) {
        if (recyclerView.getVisibility()==View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
            expandIv.setImageResource(R.drawable.ic_drop);
        } else {
            recyclerView.setVisibility(View.GONE);
            expandIv.setImageResource(R.drawable.ic_drop_hide);
        }
    }

    @Override
    public void showFromDate(String fromDate) {
        tvDateFrom.setText(fromDate);
    }

    @Override
    public void showToDate(String toDate) {
        tvDateTo.setText(toDate);
    }

    @Override
	public void showAuthors(List<AuthorModel> authors) {
        authorsAdapter = new AuthorsAdapter(getActivity(), authors);
        authorsRv.setAdapter(authorsAdapter);
        authorsAdapter.setOnAuthorsItemSelectedListener(presenter::selectAuthorFilterById);
        tvCountAuthor.setText(String.valueOf(authors.size()));
	}

    @Override
    public void showCategories(List<CategoryModel> categories) {
        categoriesAdapter = new CategoriesAdapter(getActivity(), categories);
        categoriesRv.setAdapter(categoriesAdapter);
        categoriesAdapter.setOnCategoriesItemSelectedListener(presenter::selectCategoryFilterById);

        tvCountCategory.setText(String.valueOf(categories.size()));
    }

    @Override
    public void addFilter(Filter filter) {
        searchFiltersEt.addFilter(filter);
    }

    @Override
    public void resetDate(DateType dateType) {
        switch (dateType) {
            case From:
                tvDateFrom.setText(R.string.add_date);
                break;
            case To:
                tvDateTo.setText(R.string.add_date);
                break;
        }
    }

}
