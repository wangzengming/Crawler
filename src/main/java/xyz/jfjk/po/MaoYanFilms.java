package xyz.jfjk.po;

import javax.persistence.*;
import java.util.Date;

@Table(name = "maoyan_films")
public
class MaoYanFilms {
    @Id
    //mysql主键生成策略注释
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "films_id")
    private Integer filmsId;//主键
    @Column(name = "films_name")
    private String filmsName;//电影名
    @Column(name = "films_star")
    private String filmsStar;//主演
    @Column(name = "films_release_time")
    private String filmsReleaseTime;//上映时间
    @Column(name = "films_score")
    private float filmsScore;//评分
    @Column(name = "films_summary")
    private String filmsSummary;//剧情简介
    @Column(name = "films_crawler_time")
    private Date filmsCrawlerTime;//抓取时间

    public Integer getFilmsId() {
        return filmsId;
    }

    public void setFilmsId(Integer filmsId) {
        this.filmsId = filmsId;
    }

    public String getFilmsName() {
        return filmsName;
    }

    public void setFilmsName(String filmsName) {
        this.filmsName = filmsName;
    }

    public String getFilmsStar() {
        return filmsStar;
    }

    public void setFilmsStar(String filmsStar) {
        this.filmsStar = filmsStar;
    }

    public String getFilmsReleaseTime() {
        return filmsReleaseTime;
    }

    public void setFilmsReleaseTime(String filmsRelease) {
        this.filmsReleaseTime = filmsRelease;
    }

    public float getFilmsScore() {
        return filmsScore;
    }

    public void setFilmsScore(float filmsScore) {
        this.filmsScore = filmsScore;
    }

    public String getFilmsSummary() {
        return filmsSummary;
    }

    public void setFilmsSummary(String filmsSummary) {
        this.filmsSummary = filmsSummary;
    }

    public Date getFilmsCrawlerTime() {
        return filmsCrawlerTime;
    }

    public void setFilmsCrawlerTime(Date filmsCrawlerTime) {
        this.filmsCrawlerTime = filmsCrawlerTime;
    }
}
