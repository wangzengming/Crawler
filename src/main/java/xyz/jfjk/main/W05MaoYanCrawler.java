package xyz.jfjk.main;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.jfjk.mapper.MaoYanCommentMapper;
import xyz.jfjk.mapper.MaoYanFilmsMapper;
import xyz.jfjk.po.MaoYanComment;
import xyz.jfjk.po.MaoYanFilms;
import xyz.jfjk.utils.MybatisHelper;

import java.util.Date;

/**
 * 猫眼电影抓取逻辑
 */
public class W05MaoYanCrawler {
    public static void main(String[] args)throws Exception{
        System.out.println("抓取开始...");
        SqlSession sqlSession = MybatisHelper.getSqlSessionLocal();
        MaoYanFilmsMapper maoYanFilmsMapper= sqlSession.getMapper(MaoYanFilmsMapper.class);
        MaoYanCommentMapper maoYanCommentMapper = sqlSession.getMapper(MaoYanCommentMapper.class);



        //目标网站(猫眼电影)url
        String url ="https://maoyan.com" ;
        String urlHotList = url+"/board";
        //获取网站文档对象
        Connection connect = Jsoup.connect(urlHotList);
        Document document = connect.get();
        //获取div数组
        Elements divs = document.select("div.board-item-content");
        //遍历div数组
        for (Element div:divs){
            //11获取电影名
            String filmsName = div.select(" div.movie-item-info > p.name").text();
            //12获取电影详情链接
            String filmsHref = url+div.select("div.movie-item-info > p.name > a").attr("href");
            //13获取主演
            String filmsStar = div.select(" div.movie-item-info > p.star").text();
            //14获取上映时间
            String filmsReleaseTime = div.select(" div.movie-item-info > p.releasetime").text().substring("上映时间：".length());
            //15获取评分
            String filmsScoreInteger = div.select(" div.movie-item-number >  p > i.integer").text().substring(0,1);
            String filmsScoreFraction = div.select(" div.movie-item-number > p > i.fraction").text();
            float filmsScore=0;
            //将String类型的评分转换为float类型,并相加
            try {
                filmsScore = Integer.valueOf(filmsScoreInteger).intValue()+Integer.valueOf(filmsScoreFraction).intValue()/(float)10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //16获取抓取时间
            Date filmsCrawlerTime = new Date();

            //抓取详情电影页面
            Connection connectDetails  = Jsoup.connect(filmsHref);
            Document documentDetails = connectDetails.get();
            //17获取电影简介
            String filmsSummary = documentDetails.select("div.mod-content").get(0).select("span").text();
            System.out.println(filmsName+"\t"+filmsHref+"\t"+filmsStar+"\t"+filmsReleaseTime+"\t"+filmsScore+"\t"+filmsCrawlerTime);
            System.out.println(filmsSummary);

            MaoYanFilms maoYanFilms = new MaoYanFilms();
            maoYanFilms.setFilmsName(filmsName);
            maoYanFilms.setFilmsReleaseTime(filmsReleaseTime);
            maoYanFilms.setFilmsScore(filmsScore);
            maoYanFilms.setFilmsStar(filmsStar);
            maoYanFilms.setFilmsSummary(filmsSummary);
            maoYanFilms.setFilmsCrawlerTime(filmsCrawlerTime);
            //电影简介信息入库
            maoYanFilmsMapper.insert(maoYanFilms);
            //事务提交
            sqlSession.commit();
            Integer id = maoYanFilms.getFilmsId();


            //抓取电影评论div板块
            Element commentDiv = documentDetails.select("div.comment-list-container").get(0);
            Elements commentLists = commentDiv.select("li.comment-container");
            for (Element list:commentLists){
                //21获取评论用户名
                String commentUser = list.select("span.name").text().replaceAll("[\\x{10000}-\\x{10FFFF}]","");
                //22获取评论时间
                String commentUpTime = list.select("div.time").attr("title");
                //23获取评论评分
                String commentScore = list.select("ul.score-star").attr("data-score");
                //24获取点赞数
                String num = list.select("span.num").text();
                Integer commentClickNum =0 ;
                //将String类型点赞数转为Int
                try {
                    commentClickNum  = Integer.valueOf(num).intValue();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                //25获取评论内容
                String commentContent = list.select("div.comment-content").text().replaceAll("[\\x{10000}-\\x{10FFFF}]","");
                //26获取抓取时间
                Date commentCrawlerTime = new Date();
                System.out.println(1+commentUser +"\t"+commentUpTime+"\t"+commentScore+"\t"+commentClickNum+"\t"+commentContent+"\t"+commentCrawlerTime);

                MaoYanComment maoYanComment = new MaoYanComment();
                maoYanComment.setFilmsId(id);
                maoYanComment.setCommentUser(commentUser);
                maoYanComment.setCommentContent(commentContent);
                maoYanComment.setCommentClickNum(commentClickNum);
                maoYanComment.setCommentScore(commentScore);
                maoYanComment.setCommentUpTime(commentUpTime);
                maoYanComment.setCommentCrawlerTime(commentCrawlerTime);
                //电影评论入库
                maoYanCommentMapper.insert(maoYanComment);
                //提交事务
                sqlSession.commit();
            }
            System.out.println();
        }


        //提交事务
        sqlSession.commit();
        //关流
        sqlSession.close();
        System.out.println("抓取结束...");
    }
}
