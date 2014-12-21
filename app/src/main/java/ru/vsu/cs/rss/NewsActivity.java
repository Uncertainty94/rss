package ru.vsu.cs.rss;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class NewsActivity extends ActionBarActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);
        progressBar = (ProgressBar) findViewById(R.id.list_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        MyTask mt = new MyTask();
        mt.execute();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
            String i = "qwerty";
            if (isCancelled()) {
                return "не";
            }


            try {
                HttpURLConnection connection = null;
                try {
//                    URL url = new URL("http://54.186.125.132:1337/device/checkConnect/");
                    URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=Official%20Google%20Blogs");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    connection.connect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    i = "MalformedURLException";
                } catch (ProtocolException e) {
                    e.printStackTrace();
                    i = "ProtocolException";
                }


                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    connection.disconnect();
                    return "не подкл";
                }

                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = responseReader.readLine();



                // Close connection and return response code.
                connection.disconnect();
                i = "12345";


                return line;
            } catch (IOException e) {
                e.printStackTrace();
                i = "IOException";
            }
            return i;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(NewsActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
