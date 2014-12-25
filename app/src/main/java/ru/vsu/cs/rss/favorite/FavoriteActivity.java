package ru.vsu.cs.rss.favorite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

import ru.vsu.cs.rss.R;
import ru.vsu.cs.rss.db.DBHelper;
import ru.vsu.cs.rss.news.FullNewsInfo;

/**
 * Created by max on 23.12.2014.
 */
public class FavoriteActivity extends Activity {

    private ListView favoriteList;

    FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favorite_feeds_list);
        favoriteList = (ListView) findViewById(R.id.favorite_list);
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(FavoriteActivity.this, FullNewsInfo.class);
                intent.putExtra(FullNewsInfo.EXTRA_NEWS, adapter.getItem(pos));
                startActivity(intent);
            }
        });
        DBHelper helper = DBHelper.getInstance(FavoriteActivity.this);

        try {
            Toast.makeText(FavoriteActivity.this, helper.getFeedObjectDao().getCount() + " " , Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
