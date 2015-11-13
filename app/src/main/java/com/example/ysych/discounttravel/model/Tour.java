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

    public static final String TOUR_ID = "tour_id";
    public static final String CAT_ID = "catid";
    public static final String TYPE_IMAGE = "image";

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "id", id = true)
    @SerializedName("id")
    @Expose
    int id;

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

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "images")
    @SerializedName("images")
    @Expose
    String images;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "gallery")
    @SerializedName("gallery")
    @Expose
    String gallery;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "type")
    @SerializedName("type")
    @Expose
    String type;


    @Override
    public String toString() {
        return id + ", " + category + ", " + created + ", " + modified + ", " + title + ", " + introtext + ", " + images + ", " + gallery + ", " + type;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public String getTitle() {
        return title;
    }

    public String getIntrotext() {
        return introtext;
    }

    public String getImages() {
        return images;
    }

    public String getGallery() {
        return gallery;
    }

    public String getType() {
        return type;
    }

}