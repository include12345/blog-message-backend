package com.lihebin.blog.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2018/12/2.
 */
public interface CacheDao {
    /**
     * 添加
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean cacheValue(String key, String value, long time, TimeUnit timeUnit);

    /**
     * 添加
     *
     * @param key
     * @param value
     * @return
     */
    boolean cacheValue(String key, String value);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsValueKey(String key);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsSetKey(String key);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsListKey(String key);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsKey(String key);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    String getValue(String key);

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeValue(String key);

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeSet(String key);

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeList(String key);

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean cacheSet(String key, String value, long time);

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @return
     */
    boolean cacheSet(String key, String value);

    /**
     * 缓存Set
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheSet(String k, Set<String> v, long time);

    /**
     * 缓存Set
     *
     * @param k
     * @param v
     * @return
     */
    boolean cacheSet(String k, Set<String> v);

    /**
     * 获取Set
     *
     * @param k
     * @return
     */
    Set<String> getSet(String k);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheList(String k, String v, long time);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @return
     */
    boolean cacheList(String k, String v);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheList(String k, List<String> v, long time);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @return
     */
    boolean cacheList(String k, List<String> v);

    /**
     * 获取List
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    List<String> getList(String k, long start, long end);

    /**
     * 获取页码
     *
     * @param key
     * @return
     */
    long getListSize(String key);

    /**
     * 移除list缓存
     *
     * @param k
     * @return
     */
    boolean removeOneOfList(String k);
}
