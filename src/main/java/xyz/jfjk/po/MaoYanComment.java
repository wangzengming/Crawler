package xyz.jfjk.po;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;


@Table(name = "maoyan_comment")
public class MaoYanComment {
    @Column(name = "comment_id")
    private Integer commentId;//主键
    @Column(name = "films_id")
    private Integer filmsId;//关联电影表id
    @Column(name = "comment_user")
    private String commentUser;//评论用户
    @Column(name = "comment_up_time")
    private String commentUpTime;//评论时间
    @Column(name = "comment_score")
    private String commentScore;//评分
    @Column(name = "comment_content")
    private String commentContent;//评论内容
    @Column(name = "comment_click_num")
    private Integer commentClickNum;//点赞数
    @Column(name = "comment_crawler_time")
    private Date commentCrawlerTime;//抓取时间

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getFilmsId() {
        return filmsId;
    }

    public void setFilmsId(Integer filmsId) {
        this.filmsId = filmsId;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentUpTime() {
        return commentUpTime;
    }

    public void setCommentUpTime(String commentUpTime) {
        this.commentUpTime = commentUpTime;
    }

    public String getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(String commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getCommentClickNum() {
        return commentClickNum;
    }

    public void setCommentClickNum(Integer commentClickNum) {
        this.commentClickNum = commentClickNum;
    }

    public Date getCommentCrawlerTime() {
        return commentCrawlerTime;
    }

    public void setCommentCrawlerTime(Date commentCrawlerTime) {
        this.commentCrawlerTime = commentCrawlerTime;
    }
}
