package xyz.jfjk.po;

import javax.persistence.Column;
import java.util.Date;

public class Zhihu {
    private Integer id;
    private String title;
    private String url;
    private String pic;
    @Column(name = "crawler_time")
    private Date crawlerTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getCrawlerTime() {
        return crawlerTime;
    }

    public void setCrawlerTime(Date crawlerTime) {
        this.crawlerTime = crawlerTime;
    }

    @Override
    public String toString() {
        return "Zhihu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                ", crawlerTime=" + crawlerTime +
                '}';
    }
}
