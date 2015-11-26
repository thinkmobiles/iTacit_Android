package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.groups.GetBusinessUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetGroupsUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetJobsUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetRolesUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetRecipientsSummaryUseCase;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.mappers.BusinessMapper;
import com.itacit.healthcare.presentation.messages.mappers.GroupMapper;
import com.itacit.healthcare.presentation.messages.mappers.JobMapper;
import com.itacit.healthcare.presentation.messages.mappers.RoleMapper;
import com.itacit.healthcare.presentation.messages.presenters.AddRecipientsPresenter;
import com.itacit.healthcare.presentation.messages.views.AddRecipientsView;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.RecipientNamesAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.PredefinedRecipients;
import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 16.11.15.
 */
public class AddRecipientsFragment extends BaseFragmentView<AddRecipientsPresenter, MessagesActivity> implements AddRecipientsView {
    @Bind(R.id.sv_content_FAR)          ScrollView contentSv;
    @Bind(R.id.et_search_FAR)           EditText searchRecipientsEt;
    @Bind(R.id.rv_jobs_FAR)             RecyclerView jobsRv;
    @Bind(R.id.rv_business_FAR)         RecyclerView businessRv;
    @Bind(R.id.rv_role_FAR)             RecyclerView rolesRv;
    @Bind(R.id.rv_group_FAR)            RecyclerView groupsRv;
    @Bind(R.id.tv_job_count_FAR)        TextView jobCountTv;
    @Bind(R.id.tv_business_count_FAR)   TextView businessCountTv;
    @Bind(R.id.tv_role_count_FAR)       TextView roleCountTv;
    @Bind(R.id.tv_group_count_FAR)      TextView groupCountTv;
    @Bind(R.id.iv_jobs_expand_FAR)      ImageView jobsExpandIv;
    @Bind(R.id.iv_business_expand_FAR)  ImageView businessExpandIv;
    @Bind(R.id.iv_role_expand_FAR)      ImageView roleExpandIv;
    @Bind(R.id.iv_group_expand_FAR)     ImageView groupExpandIv;
    @Bind(R.id.tv_count_recipients_FAR) TextView selectRecipientsCountTv;
    @Bind(R.id.iv_direct_rep_FAR)       ImageView directReportsIv;
    @Bind(R.id.iv_indirect_rep_FAR)     ImageView indirectReportsIv;
    @Bind(R.id.iv_coworkers_FAR)        ImageView coworkersIv;
    @Bind(R.id.iv_my_business_FAR)      ImageView myBusinessIv;
    @Bind(R.id.iv_my_job_FAR)           ImageView myJobsIv;

    @OnClick({R.id.rl_direct_reports_FAR, R.id.rl_indirect_reports_FAR, R.id.rl_my_business_FAR,
            R.id.rl_my_job_FAR, R.id.rl_co_workers_FAB})
    void onPredefinedRecipientsClick(View view) {
        switch (view.getId()) {
            case R.id.rl_direct_reports_FAR:
                presenter.predefinedClicked(PredefinedRecipients.myDirectReports);
                break;
            case R.id.rl_indirect_reports_FAR:
                presenter.predefinedClicked(PredefinedRecipients.myIndirectReports);
                break;
            case R.id.rl_my_business_FAR:
                presenter.predefinedClicked(PredefinedRecipients.myBusiness);
                break;
            case R.id.rl_my_job_FAR:
                presenter.predefinedClicked(PredefinedRecipients.myJobs);
                break;
            case R.id.rl_co_workers_FAB:
                presenter.predefinedClicked(PredefinedRecipients.myCoworkers);
                break;
        }
        showSelectedRecipients();
    }

    private void showSelectedRecipients() {
        directReportsIv.setVisibility(presenter.isRecipientSelected(PredefinedRecipients.myDirectReports) ? View.VISIBLE : View.INVISIBLE);
        indirectReportsIv.setVisibility(presenter.isRecipientSelected(PredefinedRecipients.myIndirectReports) ? View.VISIBLE : View.INVISIBLE);
        myBusinessIv.setVisibility(presenter.isRecipientSelected(PredefinedRecipients.myBusiness) ? View.VISIBLE : View.INVISIBLE);
        myJobsIv.setVisibility(presenter.isRecipientSelected(PredefinedRecipients.myJobs) ? View.VISIBLE : View.INVISIBLE);
        coworkersIv.setVisibility(presenter.isRecipientSelected(PredefinedRecipients.myCoworkers) ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick({R.id.iv_jobs_expand_FAR, R.id.iv_group_expand_FAR, R.id.iv_business_expand_FAR, R.id.iv_role_expand_FAR})
    void onExpandView(View view) {
        switch (view.getId()) {
            case R.id.iv_jobs_expand_FAR:
                AndroidUtils.toggleListVisibility(jobsRv, jobsExpandIv);
                break;
            case R.id.iv_group_expand_FAR:
                AndroidUtils.toggleListVisibility(groupsRv, groupExpandIv);
                break;
            case R.id.iv_business_expand_FAR:
                AndroidUtils.toggleListVisibility(businessRv, businessExpandIv);
                break;
            case R.id.iv_role_expand_FAR:
                AndroidUtils.toggleListVisibility(rolesRv, roleExpandIv);
                break;
        }
    }

    @OnClick(R.id.ib_clear_FAR)
    void clearSearch() {
        searchRecipientsEt.setText("");
    }

    @OnClick(R.id.ll_add_recipients_FAR)
    void addRecipients() {
        presenter.editRecipients();
    }

    @Override
    protected void setUpView() {
        showSelectedRecipients();
        jobsRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        businessRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        groupsRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rolesRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, v -> navigateToNewMessage());

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
        return new AddRecipientsPresenter(new GetBusinessUseCase(), new GetJobsUseCase(),
                new GetGroupsUseCase(), new GetRolesUseCase(), new GroupMapper(), new RoleMapper(),
                new BusinessMapper(), new JobMapper(), new GetRecipientsSummaryUseCase());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                presenter.selectRecipients();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Observable<String> getSearchRecipientsInput() {
        return RxTextView.textChangeEvents(searchRecipientsEt).map(e -> e.text().toString());
    }

    @Override
    public void showRecipients(List<RecipientModel> recipients, RecipientType type) {
        RecyclerView recyclerView;
        TextView countRecipientsTv;
        switch (type) {
            case Business:
                recyclerView = businessRv;
                countRecipientsTv = businessCountTv;
                break;
            case Job:
                recyclerView = jobsRv;
                countRecipientsTv = jobCountTv;
                break;
            case Role:
                recyclerView = rolesRv;
                countRecipientsTv = roleCountTv;
                break;
            case Group:
                recyclerView = groupsRv;
                countRecipientsTv = groupCountTv;
                break;
            default:
                return;
        }
        showRecipients(recipients, recyclerView, countRecipientsTv, type);
    }

    private void showRecipients(List<? extends RecipientModel> models, RecyclerView recyclerView, TextView itemsCountTv,
                                final RecipientType type) {
        RecipientNamesAdapter adapter = new RecipientNamesAdapter(activity, models, R.layout.list_item_recipient, presenter, type);
        recyclerView.setAdapter(adapter);
        itemsCountTv.setText(String.valueOf(models.size()));
        adapter.setRecipientSelectionListener(presenter::onRecipientClick);
    }

    @Override
    public void showRecipientsCount(int count) {
        selectRecipientsCountTv.setText(String.valueOf(count));
    }

    @Override
    public MessageStorage getMessageStorage() {
        return activity;
    }

    @Override
    public void navigateToNewMessage() {
        activity.switchContent(NewMessageFragment.class);
    }

    @Override
    public void navigateToRecipients() {
        activity.switchContent(RecipientsFragment.class);
    }
}
