package com.test;
import redis.clients.jedis.Jedis;
public class jedistest {
    public static void main(String[] args) {
        try {
            String host = "192.168.31.157";//控制台显示访问地址//r-2zec0d2743b95254.redis.rds.aliyuncs.com
            int port = 6379;
            Jedis jedis = new Jedis(host, port,4000);
            //鉴权信息
            //jedis.auth("");//password
            String key = "redis";
            String value = "aliyun-redis";
            //select db默认为0
            jedis.select(0);
            //set一个key
            jedis.set(key, value);
            System.out.println("Set Key " + key + " Value: " + value);
            //get 设置进去的key
            String getvalue = jedis.get(key);
            System.out.println("Get Key " + key + " ReturnValue: " + getvalue);
            jedis.quit();
            jedis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}