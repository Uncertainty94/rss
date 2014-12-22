package ru.vsu.cs.rss.parsers;

import android.text.Html;

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

            newsObject.setTitle(Html.fromHtml(newsInfo.getString("title")).toString());
            newsObject.setLink(newsInfo.getString("link"));
            newsObject.setAuthor(Html.fromHtml(newsInfo.getString("author")).toString());
            newsObject.setPublishedDate(Html.fromHtml(newsInfo.getString("publishedDate")).toString());
            newsObject.setContent(Html.fromHtml(newsInfo.getString("content")).toString());


            records.add(newsObject);
        }

        return records;
    }
}
