package ru.vsu.cs.rss.parsers;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.vsu.cs.rss.web.WebsiteObject;

/**
 * Created by max on 21.12.2014.
 */
public class FindFeedParser {

    public ArrayList<WebsiteObject> records = new ArrayList<>();

    public ArrayList<WebsiteObject> parse(String json) throws JSONException {

        JSONObject response = new JSONObject(json).getJSONObject("responseData");

        JSONArray dataArray = response.getJSONArray("entries");

        for (int i = 0; i < dataArray.length(); i++){

            WebsiteObject websiteObject = new WebsiteObject();

            StringBuilder data = new StringBuilder();

            JSONObject websiteInfo = dataArray.getJSONObject(i);

            websiteObject.setUrl(websiteInfo.getString("url"));
            websiteObject.setTitle(Html.fromHtml(websiteInfo.getString("title")).toString());
            websiteObject.setContent(Html.fromHtml(websiteInfo.getString("contentSnippet")).toString());
            websiteObject.setLink(websiteInfo.getString("link"));



            records.add(websiteObject);
        }

        return records;
    }
}
