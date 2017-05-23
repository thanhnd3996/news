package com.example.nguyenduythanh.news;

/**
 * Created by Nguyen Duy Thanh on 21/05/2017.
 * Đối tượng News gồm các thuộc tính của một ô tin tức
 */


public class News {

    private String title;
    private String link;
    private String image;
    private String pubDate;

    public News(String title, String link, String image, String pubDate) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLink() {
        return this.link;
    }

    public String getImage() {
        return this.image;
    }

    public String getPubDate() {
        return this.pubDate;
    }
}