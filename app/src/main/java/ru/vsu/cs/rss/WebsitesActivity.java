package ru.vsu.cs.rss;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;


public class WebsitesActivity extends ActionBarActivity {

    private EditText editText;
    private Button button;
    private ProgressBar progressBar;
    private ListView websitesList;

    WebListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websites_list);
        progressBar = (ProgressBar) findViewById(R.id.list_progress_bar);
        editText = (EditText) findViewById(R.id.website_search_layout_edit_text);
        button = (Button) findViewById(R.id.website_search_layout_btn);
        websitesList = (ListView) findViewById(R.id.website_layout_list);
        progressBar.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask mt = new MyTask();
                mt.execute();
            }
        });
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

    private void findWebClick (View view){
        MyTask mt = new MyTask();
        mt.execute();
    }

    class MyTask extends AsyncTask<Void, String, String> {

        String searchItem;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
            searchItem = editText.getText().toString();
            if (networkInfo == null || !networkInfo.isConnected()){
                Toast.makeText(WebsitesActivity.this, "Интернет не подключен", Toast.LENGTH_SHORT).show();
                cancel(true);
            } else {
                if (searchItem.isEmpty()){
                    Toast.makeText(WebsitesActivity.this, "Вы не ввели данные", Toast.LENGTH_SHORT).show();
                    cancel(true);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                }
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
                    URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=" + searchItem);
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

//            Toast.makeText(WebsitesActivity.this, result, Toast.LENGTH_SHORT).show();
            if (!result.isEmpty()) {

                FindFeedParser findFeedParser = new FindFeedParser();

                ArrayList<WebsiteObject> list = null;

                try {
                    list = findFeedParser.parse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(WebsitesActivity.this, "Trouble with parse", Toast.LENGTH_SHORT).show();
                }

                adapter = new WebListAdapter(WebsitesActivity.this, R.layout.website_list_item, list);
                websitesList.setAdapter(adapter);
//                websitesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                        Intent intent = new Intent(WebsitesActivity.this, FullInfoActivity.class);
//                        intent.putExtra(FullInfoActivity.EXTRA_BOOK, adapter.getItem(pos));
//                        startActivity(intent);
//                    }
//                });
            } else {
                Toast.makeText(WebsitesActivity.this, "Empty result", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
