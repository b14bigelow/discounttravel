package com.example.ysych.discounttravel.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ysych on 05.11.2015.
 */
public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "com.example.ysych.discounttravel.data";

    public static final String PATH_TOURS = "tours";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ToursEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOURS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOURS;


        public static final String TABLE_NAME = "tours";
        public static final String COLUMN_SITE_ID = "site_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_INTROTEXT = "introtext";
        public static final String COLUMN_CATEGORY = "catid";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_MODIFIED = "modified";

        public static Uri buildProblemsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
