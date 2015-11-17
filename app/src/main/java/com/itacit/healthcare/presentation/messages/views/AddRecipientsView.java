package com.itacit.healthcare.presentation.messages.views;


import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.BusinessModel;
import com.itacit.healthcare.presentation.messages.models.GroupModel;
import com.itacit.healthcare.presentation.messages.models.JobModel;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by root on 16.11.15.
 */
public interface AddRecipientsView extends View {
    Observable<String> getSearchRecipientsInput();
    void showBusiness(List<BusinessModel> units);
    void showJobs(List<JobModel> models);
    void showGroups(List<GroupModel> groups);
    void showSelectedRecipientsCount(int count);
    BehaviorSubject<RecipientsModel> getSelectedRecipientsSubj();
}
