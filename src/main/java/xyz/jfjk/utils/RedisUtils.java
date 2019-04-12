package xyz.jfjk.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
    private  static Jedis jedis;
    static {
        jedis = new Jedis();
    }

    /**
     * 对String类型的值进行保存
     * @param key
     * @param value
     */
    public static void insertKV(String key,String value){
        String set = jedis.set(key,value);
        System.out.println(set);
    }

    /**
     * 判断key是否已经存在
     * @param key
     * @return
     */
    public static boolean exitKey(String key){
        return jedis.exists(key);
    }

    /**
     * 删除Redis中的指定key
     * @param key
     */
    public static void deleteKey(String key){
        jedis.del(key);
    }
}
