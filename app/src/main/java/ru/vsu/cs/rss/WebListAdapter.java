package ru.vsu.cs.rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Макс on 22.10.2014.
 */
public class WebListAdapter extends ArrayAdapter<WebsiteObject> {

    public WebListAdapter(Context context, int resource, List<WebsiteObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GetInfoFromActivity infoFromActivity;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.website_list_item, null);
            infoFromActivity = new GetInfoFromActivity(convertView);
            convertView.setTag(infoFromActivity);
        } else {
            infoFromActivity = (GetInfoFromActivity) convertView.getTag();
        }

        infoFromActivity.set(getItem(position).getTitle(),
                getItem(position).getLink(),
                getItem(position).getContent());
        return convertView;
    }

    private class GetInfoFromActivity {
        TextView title;
        TextView link;
        TextView content;

        private GetInfoFromActivity(View view) {
            this.title   = (TextView) view.findViewById(R.id.website_item_title);
            this.link   = (TextView) view.findViewById(R.id.website_item_link);
            this.content = (TextView) view.findViewById(R.id.website_item_description);
        }

        public void set(String title, String link, String description) {
            this.title.setText(title);
            this.link.setText(link);
            this.content.setText(description);
        }
    }

}
