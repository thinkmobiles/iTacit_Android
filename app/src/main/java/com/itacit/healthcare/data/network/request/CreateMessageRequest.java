package com.itacit.healthcare.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

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
	private List<Map<String, String>> recipients;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReadRequiredYn() {
		return readRequiredYn;
	}

	public void setReadRequiredYn(String readRequiredYn) {
		this.readRequiredYn = readRequiredYn;
	}

	public String getReadRequiredDate() {
		return readRequiredDate;
	}

	public void setReadRequiredDate(String readRequiredDate) {
		this.readRequiredDate = readRequiredDate;
	}

	public List<Map<String, String>> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Map<String, String>> recipients) {
		this.recipients = recipients;
	}
}
