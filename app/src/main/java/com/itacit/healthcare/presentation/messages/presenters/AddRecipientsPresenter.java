package com.itacit.healthcare.presentation.messages.presenters;

import com.itacit.healthcare.data.entries.Business;
import com.itacit.healthcare.data.entries.Group;
import com.itacit.healthcare.data.entries.JobClassification;
import com.itacit.healthcare.domain.interactor.groups.GetBusinessUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetGroupsUseCase;
import com.itacit.healthcare.domain.interactor.groups.GetJobsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.BusinessMapper;
import com.itacit.healthcare.presentation.messages.mappers.GroupMapper;
import com.itacit.healthcare.presentation.messages.mappers.JobMapper;
import com.itacit.healthcare.presentation.messages.models.BusinessModel;
import com.itacit.healthcare.presentation.messages.models.GroupModel;
import com.itacit.healthcare.presentation.messages.models.JobModel;
import com.itacit.healthcare.presentation.messages.views.AddRecipientsView;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by root on 16.11.15.
 */
public class AddRecipientsPresenter extends BasePresenter<AddRecipientsView> {
    public static final int TIMEOUT = 1;
    private GetBusinessUseCase getBusinessUseCase;
    private GetJobsUseCase getJobsUseCase;
    private GetGroupsUseCase getGroupsUseCase;

    private GroupMapper groupMapper;
    private BusinessMapper businessMapper;
    private JobMapper jobMapper;


    private List<BusinessModel> businessModels;
    private List<GroupModel> groupModels;
    private List<JobModel> jobModels;

    public AddRecipientsPresenter(GetBusinessUseCase getBusinessUseCase, GetJobsUseCase getJobsUseCase,
                                  GetGroupsUseCase getGroupsUseCase, GroupMapper groupMapper,
                                  BusinessMapper businessMapper, JobMapper jobMapper) {
        this.getBusinessUseCase = getBusinessUseCase;
        this.getJobsUseCase = getJobsUseCase;
        this.getGroupsUseCase = getGroupsUseCase;
        this.groupMapper = groupMapper;
        this.businessMapper = businessMapper;
        this.jobMapper = jobMapper;
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        compositeSubscription.add(getSearchRecipientsObs().subscribe(this::getRecipients));
    }

    private Observable<String> getSearchRecipientsObs() {
        if (getView() != null) {
            return getView().getSearchRecipientsInput()
                    .filter(t -> t.length() >= NewsFeedPresenter.SEARCH_TEXT_MIN_LENGTH)
                    .debounce(TIMEOUT, TimeUnit.SECONDS);
        }
        return Observable.empty();
    }

    private void getRecipients(String query) {
        getBusinessUseCase.execute(new GetBusinessSubscriber(), query);
        getJobsUseCase.execute(new GetJobsSubscriber(), query);
        getGroupsUseCase.execute(new GetGroupsSubscriber(), query);
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


}
