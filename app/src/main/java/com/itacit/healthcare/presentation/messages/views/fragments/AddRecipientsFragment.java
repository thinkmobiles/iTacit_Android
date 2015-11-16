package com.itacit.healthcare.presentation.messages.views.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.groups.GetBusinessUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetGroupsUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetJobsUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.mappers.BusinessMapper;
import com.itacit.healthcare.presentation.messages.mappers.GroupMapper;
import com.itacit.healthcare.presentation.messages.mappers.JobMapper;
import com.itacit.healthcare.presentation.messages.models.BusinessModel;
import com.itacit.healthcare.presentation.messages.models.GroupModel;
import com.itacit.healthcare.presentation.messages.models.JobModel;
import com.itacit.healthcare.presentation.messages.presenters.AddRecipientsPresenter;
import com.itacit.healthcare.presentation.messages.views.AddRecipientsView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.RecipientAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by root on 16.11.15.
 */
public class AddRecipientsFragment extends BaseFragmentView<AddRecipientsPresenter, MessagesActivity> implements AddRecipientsView {
    @Bind(R.id.sv_content_FAR)              ScrollView contentSv;
    @Bind(R.id.et_search_FAR)               EditText searchRecipientsEt;
    @Bind(R.id.rv_jobs_FAR)                 RecyclerView jobsRv;
    @Bind(R.id.rv_business_FAR)             RecyclerView businessRv;
    @Bind(R.id.rv_role_FAR)                 RecyclerView rolesRv;
    @Bind(R.id.rv_group_FAR)                RecyclerView groupsRv;
    @Bind(R.id.tv_job_count_FAR)            TextView jobCountTv;
    @Bind(R.id.tv_business_count_FAR)       TextView businessCountTv;
    @Bind(R.id.tv_role_count_FAR)           TextView roleCountTv;
    @Bind(R.id.tv_group_count_FAR)          TextView groupCountTv;
    @Bind(R.id.iv_jobs_expand_FAR)          ImageView jobsExpandIv;
    @Bind(R.id.iv_business_expand_FAR)      ImageView businessExpandIv;
    @Bind(R.id.iv_role_expand_FAR)          ImageView roleExpandIv;
    @Bind(R.id.iv_group_expand_FAR)         ImageView groupExpandIv;


    @OnClick({R.id.iv_jobs_expand_FAR, R.id.iv_group_expand_FAR, R.id.iv_business_expand_FAR})
    void onExpandView(View view) {
        switch (view.getId()) {
            case R.id.iv_jobs_expand_FAR:
                toggleListVisibility(jobsRv, jobsExpandIv);
                break;
            case R.id.iv_group_expand_FAR:
                toggleListVisibility(groupsRv, groupExpandIv);
                break;
            case R.id.iv_business_expand_FAR:
                toggleListVisibility(businessRv, businessExpandIv);
                break;
        }
    }

    @Override
    public Observable<String> getSearchRecipientsInput() {
        return RxTextView.textChangeEvents(searchRecipientsEt).map(e -> e.text().toString());
    }

    @Override
    public void showBusiness(List<BusinessModel> business) {
        RecipientAdapter adapter = new RecipientAdapter(activity, business, R.layout.list_item_recipient);
        businessRv.setAdapter(adapter);
        businessCountTv.setText(String.valueOf(business.size()));
    }

    @Override
    public void showJobs(List<JobModel> models) {
        RecipientAdapter adapter = new RecipientAdapter(activity, models, R.layout.list_item_recipient);
        jobsRv.setAdapter(adapter);
        jobCountTv.setText(String.valueOf(models.size()));
    }

    @Override
    public void showGroups(List<GroupModel> groups) {
        RecipientAdapter adapter = new RecipientAdapter(activity, groups, R.layout.list_item_recipient);
        groupsRv.setAdapter(adapter);
        groupCountTv.setText(String.valueOf(groups.size()));
    }

    @Override
    protected void setUpView() {
        jobsRv.setLayoutManager( new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        businessRv.setLayoutManager( new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        groupsRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rolesRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
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

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, null);

        actionBar.setHomeAsUpIndicator(null);
        activity.setActionBarShadowVisible(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.add_recipients);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_recipients;
    }

    @Override
    protected AddRecipientsPresenter createPresenter() {
        return new AddRecipientsPresenter(new GetBusinessUseCase(),new GetJobsUseCase(),
                new GetGroupsUseCase(),new GroupMapper(),new BusinessMapper(),new JobMapper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
