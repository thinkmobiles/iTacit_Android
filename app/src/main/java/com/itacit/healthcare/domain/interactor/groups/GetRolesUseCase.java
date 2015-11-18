package com.itacit.healthcare.domain.interactor.groups;

import com.itacit.healthcare.data.entries.Role;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.GroupsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 18.11.15.
 */
public class GetRolesUseCase extends GetListUseCase<Role>{

	public GetRolesUseCase() {
		super(null, null);
	}

	@Override
	protected Observable<ListResponse<Role>> request() {
		return GroupsService.getApi().getRoles(requestBody);
	}
}
