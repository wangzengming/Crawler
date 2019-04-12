package xyz.jfjk.po;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name="v2ex")
public class V2ex {
    private int id;
    private String title;
    private String url;
    private String user;
    private String type;
    private String clazz;
    @Column(name="up_time")
    private String upTime;
    @Column(name="crawler_time")
    private Date crawlerTime;
    @Column(name="reply_num")
    private Integer replyNum;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
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

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    @Override
    public String toString() {
        return "V2ex{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                ", clazz='" + clazz + '\'' +
                ", upTime=" + upTime +
                ", crawlerTime=" + crawlerTime +
                ", replyNum=" + replyNum +
                '}';
    }
}
