package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.BusinessUnit;
import com.itacit.healthcare.data.network.AccessTokenHandler;
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
            api = ServiceGenerator.createService(GroupsApi.class, AccessTokenHandler.getAccessToken());
        }
        return api;
    }


    public interface GroupsApi {
        @POST("/mobile/1.0/organization/businessUnit")
        Observable<ListResponse<BusinessUnit>> getBusinessUnits(@Body ListRequest requestBody);

        @POST("/mobile/1.0/organization/jobClassification")
        Observable<ListResponse<Object>> getJobClassifications(@Body ListRequest requestBody);

        @POST("/mobile/1.0/organization/permissionGroup")
        Observable<ListResponse<Object>> getPermissionGroups(@Body ListRequest requestBody);
    }
}
