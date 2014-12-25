package ru.vsu.cs.rss.news;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by max on 21.12.2014.
 */

@DatabaseTable(tableName = "favorite")
public class NewsObject implements Serializable {

    public static final String FIELD_ID   = "field_id";
    public static final String FIELD_TITLE = "field_title";
    public static final String FIELD_AUTHOR = "field_author";
    public static final String FIELD_LINK = "field_link";
    public static final String FIELD_CONTENT = "field_content";
    public static final String FIELD_PUBLISHED_DATE = "field_publishedDate";

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = FIELD_TITLE)
    private String title;
    @DatabaseField(columnName = FIELD_AUTHOR)
    private String author;
    @DatabaseField(columnName = FIELD_LINK)
    private String link;
    @DatabaseField(columnName = FIELD_CONTENT)
    private String content;
    @DatabaseField(columnName = FIELD_PUBLISHED_DATE)
    private String publishedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public boolean equals(NewsObject o) {
        if (this.title.equals(o.getTitle())){
            return true;
        } else {
            return false;
        }
    }
}

