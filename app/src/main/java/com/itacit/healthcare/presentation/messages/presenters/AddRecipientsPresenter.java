package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Business;
import com.itacit.healthcare.data.entries.Group;
import com.itacit.healthcare.data.entries.JobClassification;
import com.itacit.healthcare.data.entries.Role;
import com.itacit.healthcare.domain.interactor.groups.GetBusinessUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetGroupsUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetJobsUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetRolesUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetRecipientsSummaryUseCase;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.BusinessMapper;
import com.itacit.healthcare.presentation.messages.mappers.GroupMapper;
import com.itacit.healthcare.presentation.messages.mappers.JobMapper;
import com.itacit.healthcare.presentation.messages.mappers.RoleMapper;
import com.itacit.healthcare.presentation.messages.views.AddRecipientsView;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.PredefinedRecipients;
import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 16.11.15.
 */
public class AddRecipientsPresenter extends BasePresenter<AddRecipientsView> {
    public static final int TIMEOUT = 1;
    private final GetRecipientsSummaryUseCase getRecipientsSummaryUseCase;
    private GetBusinessUseCase getBusinessUseCase;
    private GetJobsUseCase getJobsUseCase;
    private GetGroupsUseCase getGroupsUseCase;
    private GetRolesUseCase getRolesUseCase;

    private GroupMapper groupMapper;
    private BusinessMapper businessMapper;
    private JobMapper jobMapper;
    private RoleMapper roleMapper;

    private MessageStorage messageStorage;
    private CreateMessageModel createMessageModel;

    public AddRecipientsPresenter(GetBusinessUseCase getBusinessUseCase, GetJobsUseCase getJobsUseCase,
                                  GetGroupsUseCase getGroupsUseCase, GetRolesUseCase getRolesUseCase,
                                  GroupMapper groupMapper, RoleMapper roleMapper,
                                  BusinessMapper businessMapper, JobMapper jobMapper,
                                  GetRecipientsSummaryUseCase getRecipientsSummaryUseCase) {
        this.getBusinessUseCase = getBusinessUseCase;
        this.getJobsUseCase = getJobsUseCase;
        this.getGroupsUseCase = getGroupsUseCase;
        this.getRolesUseCase = getRolesUseCase;
        this.groupMapper = groupMapper;
        this.businessMapper = businessMapper;
        this.jobMapper = jobMapper;
        this.roleMapper = roleMapper;
        this.getRecipientsSummaryUseCase = getRecipientsSummaryUseCase;
    }

    @Override
    protected void onAttachedView(@NonNull AddRecipientsView view) {
        compositeSubscription.add(view.getSearchRecipientsInput()
                .filter(t -> t.length() >= NewsFeedPresenter.SEARCH_TEXT_MIN_LENGTH)
                .debounce(TIMEOUT, TimeUnit.SECONDS)
                .subscribe(this::getRecipients));

        messageStorage = view.getMessageStorage();
        createMessageModel = messageStorage.getMessage();
        getRecipientsCount();
    }

    private void getRecipientsCount() {
        getRecipientsSummaryUseCase.execute(s -> {
                    actOnView(view -> view.showRecipientsCount(s.getTotalRecipients()));
                },
                error -> {
                    actOnView(view -> view.showError(error.getMessage()));
                },
                createMessageModel.getRecipients());
    }

    public boolean isRecipientSelected(PredefinedRecipients predefined) {
        return createMessageModel.getRecipients().getPredefined().contains(predefined);
    }

    public boolean isRecipientSelected(String id, RecipientType type) {
        return createMessageModel.getRecipients().containsRecipient(id, type);
    }

    public void selectRecipients() {
        messageStorage.pushCreateMessage(createMessageModel);
        actOnView(AddRecipientsView::navigateToNewMessage);
    }

    public void editRecipients() {
        messageStorage.pushCreateMessage(createMessageModel);
        actOnView(AddRecipientsView::navigateToRecipients);
    }

    public void predefinedClicked(PredefinedRecipients predefined) {
        if (createMessageModel.getRecipients().getPredefined().contains(predefined)) {
            createMessageModel.getRecipients().unselectRecipients(predefined);
        } else {
            createMessageModel.getRecipients().selectRecipients(predefined);
        }
        getRecipientsCount();
    }

    private void showRecipientsOnView(List<RecipientModel> recipients, RecipientType type) {
        actOnView(view -> view.showRecipients(recipients, type));
    }

    public void onRecipientClick(RecipientModel recipient, RecipientType type) {
        if (isRecipientSelected(recipient.getId(), type)) {
            createMessageModel.getRecipients().removeRecipient(recipient.getId(), type);
        } else {
            createMessageModel.getRecipients().addRecipient(recipient, type);
        }

        getRecipientsCount();
    }

    private void getRecipients(String query) {
        getBusinessUseCase.execute(new GetBusinessSubscriber(), query);
        getJobsUseCase.execute(new GetJobsSubscriber(), query);
        getGroupsUseCase.execute(new GetGroupsSubscriber(), query);
        getRolesUseCase.execute(new GetRoleSubscriber(), query);
    }

    private final class GetBusinessSubscriber extends Subscriber<List<Business>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Business> businesses) {
            showRecipientsOnView(businessMapper.transform(businesses), RecipientType.Business);
        }
    }

    private final class GetGroupsSubscriber extends Subscriber<List<Group>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Group> groups) {
            showRecipientsOnView(groupMapper.transform(groups), RecipientType.Group);
        }
    }

    private final class GetJobsSubscriber extends Subscriber<List<JobClassification>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<JobClassification> jobClassifications) {
            showRecipientsOnView(jobMapper.transform(jobClassifications), RecipientType.Job);
        }
    }

    private final class GetRoleSubscriber extends Subscriber<List<Role>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Role> roles) {
            showRecipientsOnView(roleMapper.transform(roles), RecipientType.Role);
        }
    }
}
