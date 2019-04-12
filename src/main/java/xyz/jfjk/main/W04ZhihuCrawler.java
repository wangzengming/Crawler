package xyz.jfjk.main;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.jfjk.mapper.ZhihuMapper;
import xyz.jfjk.po.Zhihu;
import xyz.jfjk.utils.MybatisHelper;
import xyz.jfjk.utils.RedisUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class W04ZhihuCrawler {
    public static void main(String[] args)throws Exception{
        Timer timer = new Timer();

        //使用timer任务调度,调用封装的抓取方法,实现定时抓取
        //TimerTask要执行的任务   delay延时多久执行(单位毫秒)    period下次执行的时间间隔
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    crawler();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },100,60*1000);



    }
     public static void crawler() throws IOException {
        System.out.println("抓取开始...");
         //获取数据库操作对象
         SqlSession sqlSession = MybatisHelper.getSqlSessionLocal();
         ZhihuMapper sqlSessionmapper = sqlSession.getMapper(ZhihuMapper.class);


         //目标网站(知乎日报)url
         String url ="http://daily.zhihu.com" ;
         //获取网站文档对象
         Connection connect = Jsoup.connect(url);
         Document document = connect.get();
         Elements divs = document.select("div.row div.box");
         for(Element div:divs){
             //获取文章url
             String href =div.select("a").attr("href");
             String titHref = url+href;
             //获取图片链接
             String pic = div.select("img").attr("src");
             //获取文章标题
             String title = div.select("span").text();

             //去重操作
             //判断href是否存在
             boolean bool = RedisUtils.exitKey(href);
             if(bool){//如果数据存在,则进行跳过,执行下一循环
                 continue;
             }else {//如果不存在,者进行标记操作(放入Redis中),并继续执行入库操作
                 RedisUtils.insertKV(href,"1");
             }
             System.out.println(title);
             //实例化对象
             Zhihu zhihu = new Zhihu();
             //对象赋值
             zhihu.setTitle(title);
             zhihu.setUrl(titHref);
             zhihu.setPic(pic);
             zhihu.setCrawlerTime(new Date());
             try{
                  /**
                  * 正常提交时,是理想状态,不做任何操作
                  * 出现异常,说明musql未插入成功,需要将Redis中的key删除
                  */

                 //插入数据库
                 sqlSessionmapper.insert(zhihu);
                 //提交事务
                 sqlSession.commit();
             }catch(Exception e){
                RedisUtils.deleteKey(href);
             }
         }
         //关流
         sqlSession.close();
         System.out.println("抓取结束...");
     }
}
