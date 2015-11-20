package com.itacit.healthcare.presentation.messages.models;

/**
 * Created by root on 19.11.15.
 */
public class CreateReplyModel {

	private String id;
	private String SenderId;
	private String body;
	private Boolean isPrivate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return SenderId;
	}

	public void setSenderId(String senderId) {
		SenderId = senderId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
}
