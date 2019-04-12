package xyz.jfjk.po;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 百度实时热点的po对象
 */

@Table(name="baidu_news")
public class BaiduNews {
    private Integer id;
    private String keyword;
    private String type;
    private String clazz;
    @Column(name="search_num")
    private Integer searchNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Integer getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(Integer searchNum) {
        this.searchNum = searchNum;
    }

    @Override
    public String toString() {
        return "BaiduNews{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", type='" + type + '\'' +
                ", clazz='" + clazz + '\'' +
                ", searchNum=" + searchNum +
                '}';
    }
}
