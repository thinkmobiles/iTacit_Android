package com.itacit.healthcare.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nerevar on 11/12/2015.
 */
public class CreateMessageRequest {
	@SerializedName("subject")
	@Expose
	private String subject;
	@SerializedName("body")
	@Expose
	private String body;
	@SerializedName("readRequiredYn")
	@Expose
	private String readRequiredYn;
	@SerializedName("readRequiredDate")
	@Expose
	private String readRequiredDate;
	@SerializedName("recipients")
	@Expose
	private List<CreateMessageRecipient> recipients;


	private class CreateMessageRecipient {
		private String userId;
	}
}
