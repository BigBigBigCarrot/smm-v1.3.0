package com.david.test.redis;

import redis.clients.jedis.Jedis;  
import redis.clients.jedis.JedisPool;  

/**
 * 使用jedis操作redis(需开启redis服务)
 * @author david
 *
 */
public class JedisTest {  
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("localhost", 6379);//视情况配置密码
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.set("rediskey1", "redisvalue1");  
            jedis.set("rediskey2", "redisvalue2");  
            System.out.println(jedis.get("rediskey1"));  
            System.out.println(jedis.get("rediskey2"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (jedis != null)  
                jedis.close();  
        }
        jedisPool.destroy();  
    }  
}  

//直接用Jedis实例的方法，来建立一个到Redis的连接，并进行操作。但每次操作时，都建立连接，很耗费性能。
//	解决方法就是从一个连接池中取出连接对象，用完还回去。使用连接池的方案还能解决很多同步性问题。
//在Jedis中，管理Redis连接的类是JedisPool