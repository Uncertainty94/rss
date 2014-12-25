package ru.vsu.cs.rss.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.vsu.cs.rss.R;
import ru.vsu.cs.rss.news.NewsObject;

/**
 * Created by max on 23.12.2014.
 */
public class FavoriteAdapter extends ArrayAdapter<NewsObject> {

    public FavoriteAdapter(Context context, int resource, List<NewsObject> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GetInfoFromActivity infoFromActivity;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorite_feeds_item, null);
            infoFromActivity = new GetInfoFromActivity(convertView);
            convertView.setTag(infoFromActivity);
        } else {
            infoFromActivity = (GetInfoFromActivity) convertView.getTag();
        }

        infoFromActivity.set(getItem(position).getTitle(),
                getItem(position).getAuthor());
        return convertView;
    }

    private class GetInfoFromActivity {
        TextView title;
        TextView author;

        private GetInfoFromActivity(View view) {
            this.title   = (TextView) view.findViewById(R.id.favorite_item_title);
            this.author   = (TextView) view.findViewById(R.id.favorite_item_author);
        }

        public void set(String title, String author) {
            this.title.setText(title);
            this.author.setText(author);
        }
    }
}
