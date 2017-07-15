package com.david.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Jedis Cache 工具类
 * 
 * 
 * @version 2014-6-29
 */
public class JedisUtil {

	private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);

	private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);
//	private static JedisPool jedisPool = new JedisPool("localhost", 6379);

	private static final int DEFAULT_DBINDEX = -1;

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String get(String key) {
		return get(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @param dbindex
	 *            数据库index
	 * @return 值
	 */
	public static String get(String key, int dbindex) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				value = jedis.get(key);
				value = StringUtil.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
				logger.debug("get {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("get {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Object getObject(String key) {
		return getObject(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @param dbindex
	 *            数据库index
	 * @return 值
	 */
	public static Object getObject(String key, int dbindex) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				value = toObject(jedis.get(getBytesKey(key)));
				logger.debug("getObject {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String set(String key, String value, int cacheSeconds) {
		return set(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @param dbindex
	 *            数据库index
	 * @return
	 */
	public static String set(String key, String value, int cacheSeconds, int dbindex) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("set {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("set {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setObject(String key, Object value, int cacheSeconds) {
		return setObject(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @param dbindex
	 *            数据库index
	 * @return
	 */
	public static String setObject(String key, Object value, int cacheSeconds, int dbindex) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.set(getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObject {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取List缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static List<String> getList(String key) {
		return getList(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取List缓存
	 * 
	 * @param key
	 *            键
	 * @param dbindex
	 *            数据库index
	 * @return 值
	 */
	public static List<String> getList(String key, int dbindex) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				value = jedis.lrange(key, 0, -1);
				logger.debug("getList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取List缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static List<Object> getObjectList(String key) {
		return getObjectList(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取List缓存
	 * 
	 * @param key
	 *            键
	 * @param dbindex
	 *            数据库index
	 * @return 值
	 */
	public static List<Object> getObjectList(String key, int dbindex) {
		List<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
				value = Lists.newArrayList();
				for (byte[] bs : list) {
					value.add(toObject(bs));
				}
				logger.debug("getObjectList {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObjectList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置List缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setList(String key, List<String> value, int cacheSeconds) {
		return setList(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置List缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @param dbindex
	 *            数据库index
	 * @return
	 */
	public static long setList(String key, List<String> value, int cacheSeconds, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			Object[] objects=value.toArray();
			String[] strings=new String[objects.length];
			for (int i = 0; i < objects.length; i++) {
				strings[i] = objects[i].toString();
			}
			result = jedis.rpush(key, strings);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置List缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
		return setObjectList(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置List缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @param dbindex
	 *            数据库index
	 * @return
	 */
	public static long setObjectList(String key, List<Object> value, int cacheSeconds, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			result = jedis.rpush(key, value.toArray(new String[0]));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectList {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向List缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long listAdd(String key, String... value) {
		return listAdd(key, DEFAULT_DBINDEX, value);
	}

	/**
	 * 向List缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param dbindex
	 *            数据库index
	 * @return
	 */
	public static long listAdd(String key, int dbindex, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.rpush(key, value);
			logger.debug("listAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("listAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向List缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long listObjectAdd(String key, Object... value) {
		return listObjectAdd(key, DEFAULT_DBINDEX, value);
	}

	/**
	 * 向List缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param dbindex
	 *            数据库index
	 * @return
	 */
	public static long listObjectAdd(String key, int dbindex, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value) {
				list.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), ObjectUtil.serialize(list));
			logger.debug("listObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("listObjectAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Set<String> getSet(String key) {
		return getSet(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Set<String> getSet(String key, int dbindex) {
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				value = jedis.smembers(key);
				logger.debug("getSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Set<Object> getObjectSet(String key) {
		return getObjectSet(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Set<Object> getObjectSet(String key, int dbindex) {
		Set<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				value = Sets.newHashSet();
				Set<byte[]> set = jedis.smembers(getBytesKey(key));
				for (byte[] bs : set) {
					value.add(toObject(bs));
				}
				logger.debug("getObjectSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObjectSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置Set缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setSet(String key, Set<String> value, int cacheSeconds) {
		return setSet(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置Set缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setSet(String key, Set<String> value, int cacheSeconds, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.sadd(key, value.toArray(new String[0]));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setSet {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置Set缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		return setObjectSet(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置Set缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setObjectSet(String key, Set<Object> value, int cacheSeconds, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value) {
				set.add(toBytes(o));
			}
			result = jedis.sadd(getBytesKey(key), ObjectUtil.serialize(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectSet {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long setSetAdd(String key, String... value) {
		return setSetAdd(key, DEFAULT_DBINDEX, value);
	}

	/**
	 * 向Set缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long setSetAdd(String key, int dbindex, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.sadd(key, value);
			logger.debug("setSetAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSetAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long setSetObjectAdd(String key, Object... value) {
		return setSetObjectAdd(key, DEFAULT_DBINDEX, value);
	}

	/**
	 * 向Set缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long setSetObjectAdd(String key, int dbindex, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value) {
				set.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), ObjectUtil.serialize(value));
			logger.debug("setSetObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setSetObjectAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取Map缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, String> getMap(String key) {
		return getMap(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取Map缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, String> getMap(String key, int dbindex) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
				logger.debug("getMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取Map缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, Object> getObjectMap(String key) {
		return getObjectMap(key, DEFAULT_DBINDEX);
	}

	/**
	 * 获取Map缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, Object> getObjectMap(String key, int dbindex) {
		Map<String, Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				value = Maps.newHashMap();
				Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
					value.put(StringUtil.toString(e.getKey()), toObject(e.getValue()));
				}
				logger.debug("getObjectMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.warn("getObjectMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
		return setMap(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds, int dbindex) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.hmset(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		return setObjectMap(key, value, cacheSeconds, DEFAULT_DBINDEX);
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds, int dbindex) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()) {
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectMap {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("setObjectMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static String mapPut(String key, Map<String, String> value) {
		return mapPut(key, value, DEFAULT_DBINDEX);
	}

	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static String mapPut(String key, Map<String, String> value, int dbindex) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.hmset(key, value);
			logger.debug("mapPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("mapPut {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static String mapObjectPut(String key, Map<String, Object> value) {
		return mapObjectPut(key, value, DEFAULT_DBINDEX);
	}

	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static String mapObjectPut(String key, Map<String, Object> value, int dbindex) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()) {
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
			logger.debug("mapObjectPut {} = {}", key, value);
		} catch (Exception e) {
			logger.warn("mapObjectPut {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 移除Map缓存中的值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long mapRemove(String key, String mapKey) {
		return mapRemove(key, mapKey, DEFAULT_DBINDEX);
	}

	/**
	 * 移除Map缓存中的值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long mapRemove(String key, String mapKey, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.hdel(key, mapKey);
			logger.debug("mapRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 移除Map缓存中的值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long mapObjectRemove(String key, String mapKey) {
		return mapObjectRemove(key, mapKey, DEFAULT_DBINDEX);
	}

	/**
	 * 移除Map缓存中的值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long mapObjectRemove(String key, String mapKey, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean mapExists(String key, String mapKey) {
		return mapExists(key, mapKey, DEFAULT_DBINDEX);
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean mapExists(String key, String mapKey, int dbindex) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.hexists(key, mapKey);
			logger.debug("mapExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean mapObjectExists(String key, String mapKey) {
		return mapObjectExists(key, mapKey, DEFAULT_DBINDEX);
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean mapObjectExists(String key, String mapKey, int dbindex) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long del(String key) {
		return del(key, DEFAULT_DBINDEX);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long del(String key, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(key)) {
				result = jedis.del(key);
				logger.debug("del {}", key);
			} else {
				logger.debug("del {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn("del {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long delObject(String key) {
		return delObject(key, DEFAULT_DBINDEX);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long delObject(String key, int dbindex) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			if (jedis.exists(getBytesKey(key))) {
				result = jedis.del(getBytesKey(key));
				logger.debug("delObject {}", key);
			} else {
				logger.debug("delObject {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn("delObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean exists(String key) {
		return exists(key, DEFAULT_DBINDEX);
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean exists(String key, int dbindex) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.exists(key);
			logger.debug("exists {}", key);
		} catch (Exception e) {
			logger.warn("exists {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean existsObject(String key) {
		return existsObject(key, DEFAULT_DBINDEX);
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean existsObject(String key, int dbindex) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (dbindex != -1) {
				jedis.select(dbindex);
			}
			result = jedis.exists(getBytesKey(key));
			logger.debug("existsObject {}", key);
		} catch (Exception e) {
			logger.warn("existsObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取资源
	 * 
	 * @return
	 * @throws JedisException
	 */
	public static Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			logger.debug("getResource.", jedis);
		} catch (JedisException e) {
			logger.warn("getResource.", e);
			returnBrokenResource(jedis);
			throw e;
		}
		return jedis;
	}

	/**
	 * 归还资源
	 * 
	 * @param jedis
	 * @param isBroken
	 */
	public static void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}

	/**
	 * 释放资源
	 * 
	 * @param jedis
	 * @param isBroken
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取byte[]类型Key
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] getBytesKey(Object object) {
		if (object instanceof String) {
			return StringUtil.getBytes((String) object);
		} else {
			return ObjectUtil.serialize(object);
		}
	}

	/**
	 * Object转换byte[]类型
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] toBytes(Object object) {
		return ObjectUtil.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * 
	 * @param key
	 * @return
	 */
	public static Object toObject(byte[] bytes) {
		return ObjectUtil.unserialize(bytes);
	}

	/**
	 * 该方法是阻塞，所以调用的时候需要另起一个线程
	 */
	public static void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.psubscribe(jedisPubSub, patterns);
		} catch (JedisException e) {
			logger.warn("getResource.", e);
			returnBrokenResource(jedis);
			throw e;
		}
	}

}