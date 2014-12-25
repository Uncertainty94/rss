package ru.vsu.cs.rss.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.vsu.cs.rss.news.NewsObject;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;

    private FeedObjectDao feedObjectDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource conSource) {
        try {
            TableUtils.createTable(conSource, NewsObject.class);
        } catch (SQLException e) {
            Log.d("tag","Can't create database " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource conSource, int old, int next) {
        try {
            TableUtils.dropTable(conSource, NewsObject.class, true);
        } catch (SQLException e) {
            Log.d("tag","Can't drop databases "+ e);
            throw new RuntimeException(e);
        }
        onCreate(db, conSource);
    }


    public FeedObjectDao getFeedObjectDao() throws SQLException {
        if (feedObjectDao == null) {
            feedObjectDao = new FeedObjectDao(getConnectionSource());
        }
        return feedObjectDao;
    }

    public static DBHelper getInstance(Context context) {
        return OpenHelperManager.getHelper(context, DBHelper.class);
    }

}
