package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.base.widgets.chipsView.ChipsEditText;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.wheelDatePicker.WheelDatePicker;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;
import com.itacit.healthcare.presentation.news.presenters.NewsSearchPresenter;
import com.itacit.healthcare.presentation.news.views.INewsSearchView;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public class NewsSearchFragment extends BaseFragmentView<NewsSearchPresenter> implements INewsSearchView {
    @Bind(R.id.et_serch_FNS)
    ChipsEditText searchFiltersEt;
    @Bind(R.id.ib_close_FNS)
    ImageButton ibClose;
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

    @Override
    protected void setUpView() {
        ViewTreeObserver observer = searchFiltersEt.getViewTreeObserver();
        observer.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                searchFiltersEt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                searchFiltersEt.addChip("JohnCarson");
            }
        });
        datePickerWheel.setDay(25);
        datePickerWheel.setMonth(10);
        datePickerWheel.setYear(2015);
        datePickerWheel.setLocale(Locale.US);
        datePickerWheel.setVisibleItems(5);
        datePickerWheel.setMinMaxYears(2000, 2020);
        datePickerWheel.addDateChangedListener(new WheelDatePicker.IDateChangedListener() {
	        @Override
	        public void onChanged(WheelDatePicker sender, int oldDay, int oldMonth, int oldYear, int day, int month, int year) {
		        Log.i("WHEEL_APP", String.format("Selected date changed ! %02d.%02d.%04d -> %02d.%02d.%04d",
				        oldDay, oldMonth, oldYear, day, month, year));
	        }
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
        return new NewsSearchPresenter(new GetAuthorsUseCase(0, 10),new GetCategoriesUseCase(0, 10));
    }


	@OnClick({R.id.tv_from_FNS, R.id.tv_to_FNS})
	@Override
    public void showDatePicker() {
		if (datePickerWheel.getVisibility()==View.GONE) {
			datePickerWheel.setVisibility(View.VISIBLE);
		} else {
			datePickerWheel.setVisibility(View.GONE);
		}
    }



    @Override
    public Observable<String> getSearchTextObs() {
        return null;
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
	public void showAuthors(List<AuthorModel> authors) {

	}

    @Override
    public void showCategories(List<CategoryModel> categories) {

    }
}
