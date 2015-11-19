package com.itacit.healthcare.domain.interactor.groups;

import com.itacit.healthcare.data.entries.Role;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.GroupsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 18.11.15.
 */
public class GetRolesUseCase extends GetListUseCase<Role, String> {
	private final String SEARCH_PREFIX = "search:";
	@Override
	protected Observable<ListResponse<Role>> request(ListRequest requestBody) {
		return GroupsService.getApi().getRoles(requestBody);
	}

	@Override
	protected ListRequest initArgs(String query) {
		ListRequest requestBody = new ListRequest();
		requestBody.setQuery(SEARCH_PREFIX + query);
		return requestBody;
	}
}
