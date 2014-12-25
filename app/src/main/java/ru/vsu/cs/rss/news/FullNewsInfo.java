package ru.vsu.cs.rss.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import ru.vsu.cs.rss.R;
import ru.vsu.cs.rss.favorite.FavoriteActivity;

/**
 * Created by max on 22.12.2014.
 */
public class FullNewsInfo extends Activity {

    public static final String EXTRA_NEWS = "new";


    private CheckBox checkBox;

    public TextView title;
    public TextView author;
    public TextView content;
    public TextView publishedDate;
    public TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_info);

        NewsObject extraBook = (NewsObject) getIntent().getSerializableExtra(EXTRA_NEWS);

        checkBox = (CheckBox) findViewById(R.id.full_info_check);
        title = (TextView) findViewById(R.id.full_feed_title);
        title.setText(extraBook.getTitle());
        title.setSelected(true);


        author =(TextView) findViewById(R.id.full_feed_author);
        author.setText(extraBook.getAuthor());


        publishedDate =(TextView) findViewById(R.id.full_feed_date);
        publishedDate.setText(extraBook.getPublishedDate());


        content = (TextView) findViewById(R.id.full_feed_content);
        content.setText(extraBook.getContent());


        link = (TextView) findViewById(R.id.full_feed_link);
        link.setText(extraBook.getLink());

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
        if (id == R.id.favorite_feeds) {
            Intent intent = new Intent(FullNewsInfo.this, FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
