package ru.vsu.cs.rss.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.vsu.cs.rss.news.NewsObject;

/**
 * Created by max on 22.12.2014.
 */
public class LoadFeedParser {

    public ArrayList<NewsObject> records = new ArrayList<NewsObject>();

    public ArrayList<NewsObject> parse(String json) throws JSONException {

        JSONObject response = new JSONObject(json).getJSONObject("responseData").getJSONObject("feed");

        JSONArray dataArray = response.getJSONArray("entries");

        for (int i = 0; i < dataArray.length(); i++){

            NewsObject newsObject = new NewsObject();

            StringBuilder data = new StringBuilder();

            JSONObject newsInfo = dataArray.getJSONObject(i);

            newsObject.setTitle(newsInfo.getString("title"));
            newsObject.setLink(newsInfo.getString("link"));
            newsObject.setAuthor(newsInfo.getString("author"));
            newsObject.setPublishedDate(newsInfo.getString("publishedDate"));
            newsObject.setContent(newsInfo.getString("content"));


            records.add(newsObject);
        }

        return records;
    }
}
