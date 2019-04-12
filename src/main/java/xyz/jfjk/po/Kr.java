package xyz.jfjk.po;

/**
 * 36氪实体类
 */

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "36kr")
public class Kr {
    private Integer id;
    private String title;
    private String url;
    @Column(name = "up_time")
    private String upTime;
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

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public Date getCrawlerTime() {
        return crawlerTime;
    }

    public void setCrawlerTime(Date crawlerTime) {
        this.crawlerTime = crawlerTime;
    }

    @Override
    public String toString() {
        return "Kr{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", upTime='" + upTime + '\'' +
                ", crawlerTime=" + crawlerTime +
                '}';
    }
}
