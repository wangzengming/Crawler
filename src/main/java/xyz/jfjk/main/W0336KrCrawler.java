package xyz.jfjk.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.jfjk.mapper.KrMapper;
import xyz.jfjk.po.Kr;
import xyz.jfjk.utils.MybatisHelper;

import java.util.Date;

/**
 * 抓取36氪的实时热榜
 */
public class W0336KrCrawler {
    public static void main(String[] args)throws Exception{
        //获取数据库连接对象
        SqlSession sqlSession = MybatisHelper.getSqlSessionLocal();
        KrMapper krmapper = sqlSession.getMapper(KrMapper.class);
        //获取网页对象
        String url = "https://36kr.com";
        Document document = Jsoup.connect(url).get();
        Elements divs = document.select("div.hotlist-main >div");
        //获取网页script
        String text = document.select("body > script:nth-child(2)").html().substring("window.initialState=".length());
        //获取json数据
        JSONArray jsonArray = JSON.parseObject(text).getJSONObject("homeData").getJSONObject("data").getJSONObject("hotlist").getJSONArray("data");
        for (int i= 0;i<divs.size();i++){
            Element div = divs.get(i);
            //获取热点文章url
            String href = div.select("a.hotlist-item-toptwo-title").attr("href");
            if (href.equals("")||href==""){
                href = div.select("a.hotlist-item-other-title").attr("href");
            }
            href =url+ href;
            //获取文章标题
            String title = jsonArray.getJSONObject(i).getString("title");
            //文章发布时间
            String upTime=jsonArray.getJSONObject(i).getString("published_at");

            Kr kr = new Kr();
            kr.setTitle(title);
            kr.setUrl(href);
            kr.setUpTime(upTime);
            kr.setCrawlerTime(new Date());
            krmapper.insert(kr);
        }
        //提交事务
        sqlSession.commit();
        //关闭连接
        sqlSession.close();
    }
}
