package ru.vsu.cs.rss.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.vsu.cs.rss.news.NewsObject;

/**
 * Created by max on 23.12.2014.
 */
public class FeedObjectDao extends BaseDaoImpl<NewsObject, Integer> {

    public FeedObjectDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, NewsObject.class);
    }

    public NewsObject getNews(String name) throws SQLException {
        NewsObject obj = queryBuilder().where().eq(NewsObject.FIELD_TITLE, name).queryForFirst();
        return obj;
    }


    public long getCount ()throws SQLException {
        return queryBuilder().countOf();
    }
}