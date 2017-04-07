package com.example.sona.opticalillusions;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Soňa on 06-Apr-17.
 */

public final class DatabaseContract {
    private DatabaseContract() {
    }

    public static class AllIllusions implements BaseColumns {
        public static final String TABLE_NAME = "All";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLUMN_ANIMATION = "animation";
    }

    public static class FavouriteIllusions implements BaseColumns {
        public static final String TABLE_NAME = "Favourites";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLUMN_ANIMATION = "animation";
    }
}
