package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Business {

    @SerializedName("parentName")
    @Expose
    private String parentName;
    @SerializedName("nameFull")
    @Expose
    private String nameFull;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("typeId")
    @Expose
    private String typeId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("parentId")
    @Expose
    private String parentId;

    /**
     * @return The parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName The parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return The nameFull
     */
    public String getNameFull() {
        return nameFull;
    }

    /**
     * @param nameFull The nameFull
     */
    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName The typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return The typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId The typeId
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId The parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}