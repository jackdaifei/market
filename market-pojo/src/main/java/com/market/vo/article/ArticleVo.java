package com.market.vo.article;

import java.util.Date;

/**
 * Created by FLY on 2017/8/27.
 */
public class ArticleVo {

    private String title;

    private String content;

    private Date publishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
