package ru.vsu.cs.rss.web;

import java.io.Serializable;

/**
 * Created by max on 22.12.2014.
 */
public class WebsiteObject implements Serializable {

    private String title, link, content, url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

