package com.example.ysych.discounttravel.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ysych on 05.11.2015.
 */
public class TourDAO extends BaseDaoImpl<Tour, Integer> {

    public TourDAO(ConnectionSource connectionSource,
                   Class<Tour> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Tour> getAllRoles() throws SQLException {
        return this.queryForAll();
    }
}