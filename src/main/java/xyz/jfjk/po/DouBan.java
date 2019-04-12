package xyz.jfjk.po;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "douban")
public class DouBan {

    //id主键
    private Integer id;
    //视频类型
    private String type;
    //视频名称
    private String title;
    //标签
    private String tag;
    //视频评分
    private String rate;
    //视频链接
    @Column(name = "url")
    private String vUrl;
    //视频图片链接
    private String cover;

    //爬取时间
    @Column(name = "crawler_time")
    private Date crawlerTime;

    //是否是最新影片
    @Column(name = "is_new")
    private String isNew;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCrawlerTime() {
        return crawlerTime;
    }

    public void setCrawlerTime(Date crawlerTime) {
        this.crawlerTime = crawlerTime;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}
