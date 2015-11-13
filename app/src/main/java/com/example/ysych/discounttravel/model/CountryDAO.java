package com.example.ysych.discounttravel.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by ysych on 12.11.2015.
 */
public class CountryDAO extends BaseDaoImpl<Country, Integer> {

    public CountryDAO(ConnectionSource connectionSource,
                      Class<Country> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
