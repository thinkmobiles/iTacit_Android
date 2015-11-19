package com.itacit.healthcare.data.entries;

/**
 * Created by Den on 17.11.15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipient {

    @SerializedName("nameFull")
    @Expose
    private String nameFull;
    @SerializedName("readConfirmedDateTime")
    @Expose
    private String readConfirmedDateTime;
    @SerializedName("imageAssetId")
    @Expose
    private String imageAssetId;
    @SerializedName("readConfirmedYn")
    @Expose
    private String readConfirmedYn;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("messageId")
    @Expose
    private String messageId;
    @SerializedName("employeeId")
    @Expose
    private String employeeId;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The nameFull
     */
    public String getNameFull() {
        return nameFull;
    }

    /**
     *
     * @param nameFull
     * The nameFull
     */
    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    /**
     *
     * @return
     * The readConfirmedDateTime
     */
    public String getReadConfirmedDateTime() {
        return readConfirmedDateTime;
    }

    /**
     *
     * @param readConfirmedDateTime
     * The readConfirmedDateTime
     */
    public void setReadConfirmedDateTime(String readConfirmedDateTime) {
        this.readConfirmedDateTime = readConfirmedDateTime;
    }

    /**
     *
     * @return
     * The imageAssetId
     */
    public String getImageAssetId() {
        return imageAssetId;
    }

    /**
     *
     * @param imageAssetId
     * The imageAssetId
     */
    public void setImageAssetId(String imageAssetId) {
        this.imageAssetId = imageAssetId;
    }

    /**
     *
     * @return
     * The readConfirmedYn
     */
    public String getReadConfirmedYn() {
        return readConfirmedYn;
    }

    /**
     *
     * @param readConfirmedYn
     * The readConfirmedYn
     */
    public void setReadConfirmedYn(String readConfirmedYn) {
        this.readConfirmedYn = readConfirmedYn;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     *
     * @param roleName
     * The roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     *
     * @return
     * The messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     *
     * @param messageId
     * The messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     *
     * @return
     * The employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * @param employeeId
     * The employeeId
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
