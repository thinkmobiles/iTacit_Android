package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 18.11.15.
 */
public class Role {

	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("parentId")
	@Expose
	private String parentId;
	@SerializedName("parentName")
	@Expose
	private String parentName;
	@SerializedName("jobClassificationId")
	@Expose
	private String jobClassificationId;
	@SerializedName("jobClassificationName")
	@Expose
	private String jobClassificationName;
	@SerializedName("businessUnitId")
	@Expose
	private String businessUnitId;
	@SerializedName("businessUnitName")
	@Expose
	private String businessUnitName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getJobClassificationId() {
		return jobClassificationId;
	}

	public void setJobClassificationId(String jobClassificationId) {
		this.jobClassificationId = jobClassificationId;
	}

	public String getJobClassificationName() {
		return jobClassificationName;
	}

	public void setJobClassificationName(String jobClassificationName) {
		this.jobClassificationName = jobClassificationName;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}
}
