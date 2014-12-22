package ru.vsu.cs.rss.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ru.vsu.cs.rss.websites.WebsiteObject1;

/**
 * Created by max on 21.12.2014.
 */
public class FindFeedParser {

    public ArrayList<WebsiteObject1> records = new ArrayList<>();

    public ArrayList<WebsiteObject1> parse(String json) throws JSONException {

        JSONObject response = new JSONObject(json).getJSONObject("responseData");

        JSONArray dataArray = response.getJSONArray("entries");

        for (int i = 0; i < dataArray.length(); i++){

            WebsiteObject1 websiteObject = new WebsiteObject1();

            StringBuilder data = new StringBuilder();

            JSONObject websiteInfo = dataArray.getJSONObject(i);

            try {
                websiteObject.setUrl(new URL(websiteInfo.getString("url")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            websiteObject.setTitle(websiteInfo.getString("title"));
            websiteObject.setContent(websiteInfo.getString("contentSnippet"));
            websiteObject.setLink(websiteInfo.getString("link"));


            records.add(websiteObject);
        }

        return records;
    }
}
