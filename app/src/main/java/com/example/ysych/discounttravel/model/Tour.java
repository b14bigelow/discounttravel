package com.example.ysych.discounttravel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ysych on 05.11.2015.
 */
@DatabaseTable(tableName = "tours")
public class Tour {

    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.STRING, columnName = "id")
    String id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "siteid")
    @SerializedName("id")
    @Expose
    String siteId;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "catid")
    @SerializedName("catid")
    @Expose
    String category;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "created")
    @SerializedName("created")
    @Expose
    String created;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "modified")
    @SerializedName("modified")
    @Expose
    String modified;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "title")
    @SerializedName("title")
    @Expose
    String title;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "introtext")
    @SerializedName("introtext")
    @Expose
    String introtext;

}