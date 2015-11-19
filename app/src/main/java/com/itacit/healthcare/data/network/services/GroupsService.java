package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.Business;
import com.itacit.healthcare.data.entries.Group;
import com.itacit.healthcare.data.entries.JobClassification;
import com.itacit.healthcare.data.entries.Role;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;


/**
 * Created by root on 13.11.15.
 */
public class GroupsService {
    private static GroupsApi api;

    public static GroupsApi getApi() {
        if (api == null) {
            api = ServiceGenerator.createService(GroupsApi.class, true);
        }
        return api;
    }

    public interface GroupsApi {
        @POST("/mobile/1.0/organization/businessUnit")
        Observable<ListResponse<Business>> getBusinessUnits(@Body ListRequest requestBody);

        @POST("/mobile/1.0/organization/jobClassification")
        Observable<ListResponse<JobClassification>> getJobClassifications(@Body ListRequest requestBody);

        @POST("/mobile/1.0/organization/permissionGroup")
        Observable<ListResponse<Group>> getPermissionGroups(@Body ListRequest requestBody);

        @POST("/mobile/1.0/organization/role")
        Observable<ListResponse<Role>> getRoles(@Body ListRequest requestBody);
    }
}
