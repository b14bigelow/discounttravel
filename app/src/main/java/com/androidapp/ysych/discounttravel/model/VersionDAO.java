package com.androidapp.ysych.discounttravel.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by ysych on 07.12.2015.
 */
public class VersionDAO extends BaseDaoImpl<Version, Integer> {

    public VersionDAO(ConnectionSource connectionSource,
                   Class<Version> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}