package xyz.jfjk.main;

import org.jsoup.Jsoup;

import java.io.IOException;

import static sun.misc.PostVMInitHook.run;

/**
 * 慕课手记文章刷阅读量
 */
public class W07ImoocCrawler {
    public static void main(String[] args)throws Exception{
        for (int i = 0; i < 1000; i++) {
           new Thread( new MyThread()).start();
           Thread.sleep(30);
        }
    }

}
class  MyThread extends  Thread{
    @Override
    public void run() {
        //刷阅读量的URl
        String url = "http://www.imooc.com/article/284909";
        try {
            //模拟请求目标文章
            Jsoup.connect(url).get();
            //输出线程名称
            System.out.println(Thread.currentThread().getName()+"执行完成!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(Thread.currentThread().getName()+"执行失败!");
        }
    }
}