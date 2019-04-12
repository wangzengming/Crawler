package xyz.jfjk.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xyz.jfjk.mapper.DouBanMapper;
import xyz.jfjk.po.DouBan;
import xyz.jfjk.utils.MybatisHelper;

import java.util.Date;

/**
 * 电影节Json接口                ---类型 --子分类 --每页个数 --第几个开始
 * https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0
 * https://movie.douban.com/j/search_subjects?type=movie&tag=热门&page_limit=50&page_start=0
 * https://movie.douban.com/j/search_subjects?type=movie&tag=综艺&page_limit=50&page_start=0
 * https://movie.douban.com/j/search_subjects?type=movie&tag=美剧&page_limit=50&page_start=0
 * https://movie.douban.com/j/search_subjects?type=movie&tag=日剧&page_limit=50&page_start=0
 * 电视剧的Json接口
 * https://movie.douban.com/j/search_subjects?type=tv&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0
 *
 */
public class W06DouBanCrawler {
    public static void main(String[] args)throws Exception{
        System.out.println("抓取开始...");
        SqlSession sqlSession = MybatisHelper.getSqlSessionLocal();
        DouBanMapper douBanMapper= sqlSession.getMapper(DouBanMapper.class);



        //构造参数
        String[] types = {"movie","tv"};
        String[] movieTags ={"热门","最新","豆瓣高分","冷门佳片","华语","欧美","韩国","日本"};
        String[] tvTags =   {"热门","国产剧","综艺","美剧","日剧","韩剧","日本动画","纪录片"};
        String[] tags;
        //遍历type
        for (String type:types){
            if (type.equals("movie")){
                tags = movieTags;
            }else {
                tags= tvTags;
            }
            //遍历tags标签
            for (String tag:tags){
                String url ="https://movie.douban.com/j/search_subjects?type="+type+"&tag="+tag+"&page_limit=50&page_start=0";
                Document document = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .get();
                String jsonStr = document.text();
                JSONArray subjects = JSON.parseObject(jsonStr).getJSONArray("subjects");
                for (Object json:subjects){
                    /**
                    *             "rate": "6.4",
                    *             "cover_x": 800,
                    *             "title": "玻璃先生",
                    *             "url": "https://movie.douban.com/subject/27031237/",
                    *             "playable": false,
                    *             "cover": "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541258858.jpg",
                    *             "id": "27031237",
                    *             "cover_y": 1143,
                    *             "is_new": false
                    */
                    JSONObject jsonObject = JSON.parseObject(json.toString());
                    //获取视频名称
                    String title = jsonObject.getString("title");
                    //获取视频评分
                    String rate = jsonObject.getString("rate");
                    //获取视频链接
                    String vUrl = jsonObject.getString("url");
                    //获取视频图片链接
                    String cover = jsonObject.getString("cover");
                    //获取是否是最新影片
                    String IsNew = jsonObject.getString("is_new");

                    //实例化对象,并赋值
                    DouBan douBan = new DouBan();
                    douBan.setTitle(title);
                    douBan.setType(type);
                    douBan.setTag(tag);
                    douBan.setRate(rate);
                    douBan.setvUrl(vUrl);
                    douBan.setCover(cover);
                    douBan.setIsNew(IsNew);
                    douBan.setCrawlerTime(new Date());
                    //入库
                    douBanMapper.insert(douBan);
                    //提交事务
                    sqlSession.commit();
                }
            }
        }
        //关流
        sqlSession.close();
        System.out.println("抓取结束...");
    }
}
