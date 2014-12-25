package ru.vsu.cs.rss.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import ru.vsu.cs.rss.R;
import ru.vsu.cs.rss.db.DBHelper;
import ru.vsu.cs.rss.favorite.FavoriteActivity;
import ru.vsu.cs.rss.parsers.LoadFeedParser;

/**
 * Created by max on 22.12.2014.
 */
public class NewsActivity extends Activity {

    public static String EXTRA_URL;

    private ProgressBar progressBar;
    private ListView newsList;

    NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);
        progressBar = (ProgressBar) findViewById(R.id.news_progress_bar);
        newsList = (ListView) findViewById(R.id.news_layout_list);
        progressBar.setVisibility(View.INVISIBLE);
//        Toast.makeText(NewsActivity.this, EXTRA_URL, Toast.LENGTH_SHORT).show();
        LoadFeed loadFeed = new LoadFeed();
        loadFeed.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.favorite_feeds) {
            Intent intent = new Intent(NewsActivity.this, FavoriteActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadFeed extends AsyncTask<Void, String, String> {

        String searchItem;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()){
                Toast.makeText(NewsActivity.this, "Интернет не подключен", Toast.LENGTH_SHORT).show();
                cancel(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected String doInBackground(Void... params) {
            if (isCancelled()) {
                return "Cancelled";
            }


            try {
                HttpURLConnection connection = null;
                try {
//                    URL url = new URL("http://54.186.125.132:1337/device/checkConnect/");
                    URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=" + EXTRA_URL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    connection.connect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "MalformedURLException";
                } catch (ProtocolException e) {
                    e.printStackTrace();
                    return "ProtocolException";
                }


                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    connection.disconnect();
                    return "Error Code: 200";
                }

                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = responseReader.readLine();

                connection.disconnect();

                return line;
            } catch (IOException e) {
                e.printStackTrace();
                return "IOException";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);

//            Toast.makeText(NewsActivity.this, result, Toast.LENGTH_SHORT).show();
            if (!result.isEmpty()) {

                LoadFeedParser loadFeedParser = new LoadFeedParser();

                ArrayList<NewsObject> list = null;

                try {
                    list = loadFeedParser.parse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NewsActivity.this, "Trouble with parse", Toast.LENGTH_SHORT).show();
                }
                adapter = new NewsListAdapter(NewsActivity.this, R.layout.news_item, list);
                if (adapter != null){
                newsList.setAdapter(adapter);
                newsList.setSelected(true);
                newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        DBHelper helper = DBHelper.getInstance(NewsActivity.this);
                        try {
                            helper.getFeedObjectDao().update(adapter.getItem(pos));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(NewsActivity.this, FullNewsInfo.class);
                        intent.putExtra(FullNewsInfo.EXTRA_NEWS, adapter.getItem(pos));
                        startActivity(intent);
                    }
                });} else {
                    Toast.makeText(NewsActivity.this, "Null result", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(NewsActivity.this, "Empty result", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
