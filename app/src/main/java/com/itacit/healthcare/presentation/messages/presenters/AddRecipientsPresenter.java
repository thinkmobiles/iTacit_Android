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
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.BusinessMapper;
import com.itacit.healthcare.presentation.messages.mappers.GroupMapper;
import com.itacit.healthcare.presentation.messages.mappers.JobMapper;
import com.itacit.healthcare.presentation.messages.mappers.RoleMapper;
import com.itacit.healthcare.presentation.messages.models.BusinessModel;
import com.itacit.healthcare.presentation.messages.models.GroupModel;
import com.itacit.healthcare.presentation.messages.models.JobModel;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel;
import com.itacit.healthcare.presentation.messages.models.RoleModel;
import com.itacit.healthcare.presentation.messages.views.AddRecipientsView;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

import static com.itacit.healthcare.presentation.messages.models.RecipientsModel.PredefinedRecipients;
import static com.itacit.healthcare.presentation.messages.models.RecipientsModel.RecipientType;

/**
 * Created by root on 16.11.15.
 */
public class AddRecipientsPresenter extends BasePresenter<AddRecipientsView> {
    public static final int TIMEOUT = 1;
    private GetBusinessUseCase getBusinessUseCase;
    private GetJobsUseCase getJobsUseCase;
    private GetGroupsUseCase getGroupsUseCase;
    private GetRolesUseCase getRolesUseCase;

    private GroupMapper groupMapper;
    private BusinessMapper businessMapper;
    private JobMapper jobMapper;
    private RoleMapper roleMapper;


    private List<BusinessModel> businessModels;
    private List<GroupModel> groupModels;
    private List<JobModel> jobModels;
    private List<RoleModel> roleModels;

    private RecipientsModel recipients = new RecipientsModel();

    public AddRecipientsPresenter(GetBusinessUseCase getBusinessUseCase, GetJobsUseCase getJobsUseCase,
                                  GetGroupsUseCase getGroupsUseCase, GetRolesUseCase getRolesUseCase,
                                  GroupMapper groupMapper, RoleMapper roleMapper,
                                  BusinessMapper businessMapper, JobMapper jobMapper) {
        this.getBusinessUseCase = getBusinessUseCase;
        this.getJobsUseCase = getJobsUseCase;
        this.getGroupsUseCase = getGroupsUseCase;
        this.getRolesUseCase = getRolesUseCase;
        this.groupMapper = groupMapper;
        this.businessMapper = businessMapper;
        this.jobMapper = jobMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    protected void onAttachedView(@NonNull AddRecipientsView view) {
        compositeSubscription.add(view.getSearchRecipientsInput()
                .filter(t -> t.length() >= NewsFeedPresenter.SEARCH_TEXT_MIN_LENGTH)
                .debounce(TIMEOUT, TimeUnit.SECONDS)
                .subscribe(this::getRecipients));

        compositeSubscription.add(view.getSelectedRecipientsSubj().subscribe(recipientsModel -> {
            this.recipients = recipientsModel;

            actOnView(v -> v.showSelectedRecipientsCount(recipientsModel.getRecipientsCount()));
        }));
    }

    public boolean isRecipientSelected(PredefinedRecipients predefined) {
        return recipients.getPredefined().contains(predefined);
    }


    public boolean isRecipientSelected(String id, RecipientType type) {
        return recipients.containsRecipient(id, type);
    }

    public void selectRecipients() {
        actOnView(view -> view.getSelectedRecipientsSubj().onNext(recipients));
    }

    public void selectPredefined(PredefinedRecipients predefinedRecipients) {
        recipients.selectRecipients(predefinedRecipients);
    }

    public void predefinedClicked(PredefinedRecipients predefined) {
        if (recipients.getPredefined().contains(predefined)) {
            recipients.unselectRecipients(predefined);
        } else {
            recipients.selectRecipients(predefined);
        }
    }

    public void onRecipientClick(String id, RecipientType type) {
        if (isRecipientSelected(id, type)) {
            recipients.removeRecipient(id, type);
        } else {
            recipients.addRecipient(id, type);
        }

        actOnView(view -> view.showSelectedRecipientsCount(recipients.getRecipientsCount()));
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
            actOnView(view -> view.showBusiness(businessModels));
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Business> businesses) {
            businessModels = businessMapper.transform(businesses);
        }
    }

    private final class GetGroupsSubscriber extends Subscriber<List<Group>> {

        @Override
        public void onCompleted() {
            actOnView(view -> view.showGroups(groupModels));
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Group> groups) {
            groupModels = groupMapper.transform(groups);
        }
    }

    private final class GetJobsSubscriber extends Subscriber<List<JobClassification>> {

        @Override
        public void onCompleted() {
            actOnView(view -> view.showJobs(jobModels));
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<JobClassification> jobClassifications) {
            jobModels = jobMapper.transform(jobClassifications);
        }
    }

	private final class GetRoleSubscriber extends Subscriber<List<Role>> {

		@Override
		public void onCompleted() {
			actOnView(view -> view.showRoles(roleModels));
		}

		@Override
		public void onError(Throwable e) {
		}

		@Override
		public void onNext(List<Role> roles) {
			roleModels = roleMapper.transform(roles);
		}
	}
}
