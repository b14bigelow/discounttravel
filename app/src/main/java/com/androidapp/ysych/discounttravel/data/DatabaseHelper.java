package com.androidapp.ysych.discounttravel.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidapp.ysych.discounttravel.model.Country;
import com.androidapp.ysych.discounttravel.model.CountryDAO;
import com.androidapp.ysych.discounttravel.model.Tour;
import com.androidapp.ysych.discounttravel.model.TourDAO;
import com.androidapp.ysych.discounttravel.model.Version;
import com.androidapp.ysych.discounttravel.model.VersionDAO;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "discount.db";

    private TourDAO tourDAO = null;
    private CountryDAO countryDAO = null;
    private VersionDAO versionDAO = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource, Tour.class);
            TableUtils.createTable(connectionSource, Country.class);
            TableUtils.createTable(connectionSource, Version.class);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, Tour.class, true);
            TableUtils.dropTable(connectionSource, Country.class, true);
            TableUtils.dropTable(connectionSource, Version.class, true);
            onCreate(database, connectionSource);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public TourDAO getTourDAO() throws SQLException{
        if(tourDAO == null){
            tourDAO = new TourDAO(getConnectionSource(), Tour.class);
        }
        return tourDAO;
    }

    public CountryDAO getCountryDAO() throws SQLException{
        if(countryDAO == null){
            countryDAO = new CountryDAO(getConnectionSource(), Country.class);
        }
        return countryDAO;
    }

    public VersionDAO getVersionDAO() throws SQLException{
        if(versionDAO == null){
            versionDAO = new VersionDAO(getConnectionSource(), Version.class);
        }
        return versionDAO;
    }

    @Override
    public void close(){
        super.close();
        tourDAO = null;
        countryDAO = null;
        versionDAO = null;
    }
}
