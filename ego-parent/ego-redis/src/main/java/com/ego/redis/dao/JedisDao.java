package com.ego.redis.dao;

public interface JedisDao {
	/**
	 * 判断是否存在对应的键值对
	 * @param key
	 * @return
	 */
	boolean exists(String key);
	
	/**
	 * 删除对应键
	 * @param key
	 * @return
	 */
	Long del(String key);
	
	/**
	 * 存储对应键的数据
	 * @param key
	 * @return
	 */
	String set(String key,String value);
	
	/**
	 * 取出对应键的值
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 设置Jedis中key对应数据的过期时间啊
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);
}
