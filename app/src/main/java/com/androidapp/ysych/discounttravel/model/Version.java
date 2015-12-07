package com.androidapp.ysych.discounttravel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "current_version")
public class Version {
    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;

    }

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "id", id = true)
    int id;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "version")
    @SerializedName("version")
    @Expose
    int version;
}
