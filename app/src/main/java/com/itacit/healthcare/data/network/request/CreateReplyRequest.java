package com.itacit.healthcare.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 19.11.15.
 */
public class CreateReplyRequest {

	@SerializedName("messageId")
	@Expose
	private String messageId;
	@SerializedName("senderID")
	@Expose
	private String senderID;
	@SerializedName("body")
	@Expose
	private String body;
	@SerializedName("privateYn")
	@Expose
	private String privateYn;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPrivateYn() {
		return privateYn;
	}

	public void setPrivateYn(String privateYn) {
		this.privateYn = privateYn;
	}
}
