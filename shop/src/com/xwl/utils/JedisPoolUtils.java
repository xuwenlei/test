package com.xwl.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
	
	private static JedisPool pool = null;
	
	static{
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(30);
		poolConfig.setMaxTotal(100);
		poolConfig.setMinIdle(10);
		pool = new JedisPool(poolConfig,"localhost", 6379);
	}
	
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
}
