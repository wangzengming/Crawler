package xyz.jfjk.main;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.jfjk.mapper.BaiduNewsMapper;
import xyz.jfjk.po.BaiduNews;
import xyz.jfjk.utils.MybatisHelper;

import java.io.UnsupportedEncodingException;
/**
 * 抓取百度实时热点的功能实现
 */
public class W01BaiduNewsCrawler {
    public static void main(String[] args)throws Exception{
        //获取sqlSession
        SqlSession sqlSession = MybatisHelper.getSqlSessionLocal();
        //注入要操作的表mapper,方便单表操作
        BaiduNewsMapper baiduNewsMapper = sqlSession.getMapper(BaiduNewsMapper.class);
        //分析网站结构,获取数据并入库
        //需要抓取的url
        String url  ="http://top.baidu.com/buzz?b=1";
        //使用爬虫库获取
        Document doc = Jsoup.connect(url).get();
        getElementAndInsrt(doc,baiduNewsMapper, "实时热点");
        //获取新闻列表
        Elements lis = doc.select("#flist  li");
        for (int i=2;i<lis.size();i++){
            Element li = lis.get(i);
            //attr获取属性
            String title = li.select("a").attr("title");
            String href = "http://top.baidu.com"+li.select("a").attr("href").substring(1);
            System.out.println(title+"----"+href);
            doc = Jsoup.connect(href).get();
            getElementAndInsrt(doc,baiduNewsMapper, title);
        }
        //提交事务
        sqlSession.commit();
        //关闭流
        sqlSession.close();
    }
    public static void getElementAndInsrt(Document doc, BaiduNewsMapper baiduNewsMapper, String type) throws UnsupportedEncodingException {
        //使用元素选择器,获取对应的html
        Elements trs = doc.select("#main > div.mainBody > div > table tr");
        //遍历获取所有需要的值
        for (Element tr:trs) {
            //获取页面上所需要的值
            String keyword = tr.select(".list-title").text();
            String clazz = tr.select(".tc").text();
            String TempNum = tr.select(".last").text();
            int num;
            try {
                num = Integer.parseInt(TempNum);
            }catch (Exception e){
                continue;
            }
            //封装到对象中
            BaiduNews baiduNews = new BaiduNews();
            baiduNews.setKeyword(keyword);
            baiduNews.setClazz(clazz);
            baiduNews.setSearchNum(num);
            baiduNews.setType(type);
            System.out.println(baiduNews);
            //入库
            System.out.println("入库:"+keyword);
            baiduNewsMapper.insert(baiduNews);
        }

    }
}
