package xyz.jfjk.main;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.jfjk.mapper.V2exMapper;
import xyz.jfjk.po.V2ex;
import xyz.jfjk.utils.MybatisHelper;

import java.util.Date;

/**
 * V2ex的抓取逻辑
 */
public class W02V2exCrawler {
    public static void main(String[] args)throws Exception{
        //获取session
        SqlSession sqlSession =  MybatisHelper.getSqlSessionLocal();
        //注入要操作的表mapper,方便单表操作
        V2exMapper v2exMapper = sqlSession.getMapper(V2exMapper.class);

        //分析网站结构,获取数据并入库
        //需要抓取的url
        String url  ="https://www.v2ex.com";
        //获取网页文档对象
        Document doc = Jsoup.connect(url).get();
        //获取元素集合
        Elements as = doc.select("#Tabs a");
        for (Element a:as) {
            //获取大分类url
            String href = a.attr("href");
            //跳过这两个分类,这两个分类里面的内容是其他分类的聚合,抓取的其他分类,这两个分类以包括
            if (href.equals("/?tab=hot")||href.equals("/?tab=all")){
                continue;
            }
            href = url+href;
            //获取大分类
            String type = a.text();
            doc = Jsoup.connect(href).get();
            Elements divs = doc.select("div .cell.item");
            for (Element div:divs) {
                //获取文章链接
                href = url+div.select(" span.item_title a").attr("href");
                //获取文章标题
                String title =div.select(" span.item_title a").text().replaceAll("[\\x{10000}-\\x{10FFFF}]","");
                //获取回帖数
                String tempReplyNum = div.select("a.count_livid").text();
                Integer replyNum = null;
                try {
                    replyNum = Integer.parseInt(tempReplyNum);
                } catch (NumberFormatException e) {
                }
                //设置抓取时间
                Date date = new Date();

                //临时值的结构   Linux  •  zeroze  •  1 小时 54 分钟前  •  最后回复来自 weizongwei55555
                String[] temp = div.select("span.topic_info").text().trim().replaceFirst("[1-9]","").trim().split(" ");
                //获取小标题
                String clazz=null;
                //获取用户名
                String user = null;
                //获取发帖的相对时间
                String time = null;
                String upTime=null;
                try{
                    clazz = temp[0];
                    user = temp[2];
                    time = temp[5];
                    if (time.equals("小时")){
                        upTime = temp[4]+temp[5]+ temp[6]+temp[7];
                    } else{
                        upTime = temp[4]+temp[5];
                    }
                }catch (Exception e){
                }

                //创建数据对象
                V2ex v2ex  = new V2ex();
                //赋值
                v2ex.setTitle(title);
                v2ex.setUrl(href);
                v2ex.setUser(user);
                v2ex.setType(type);
                v2ex.setClazz(clazz);
                v2ex.setUpTime(upTime);
                v2ex.setCrawlerTime(date);
                v2ex.setReplyNum(replyNum);


                System.out.println(v2ex);
                //数据入库
                v2exMapper.insert(v2ex);
            }
        }
        //事务提交
        sqlSession.commit();
        //关闭连接
        sqlSession.close();
    }
}
