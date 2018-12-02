package com.lihebin.blog.dao.impl;

import com.lihebin.blog.dao.CacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2018/12/2.
 */
@Repository
public class CacheDaoImpl implements CacheDao {


    private static final String KEY_PREFIX_VALUE = "blog_message_backend_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean cacheValue(String key, String value, long time, TimeUnit timeUnit) {
        if(value.equalsIgnoreCase(getValue(key))) {
            return false;
        }
        key = String.format("%s%s", KEY_PREFIX_VALUE, key);
        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            boolean status = valueOps.setIfAbsent(key, value);
            if (!status) {
                return false;
            }
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean cacheValue(String key, String value) {
        return cacheValue(key, value, -1, TimeUnit.SECONDS);
    }

    @Override
    public boolean containsValueKey(String key) {
        return containsKey(key);
    }

    @Override
    public boolean containsSetKey(String key) {
        return containsKey(key);
    }

    @Override
    public boolean containsListKey(String key) {
        return containsKey(key);
    }

    @Override
    public boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public String getValue(String key) {
        key = String.format("%s%s", KEY_PREFIX_VALUE, key);
        try {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            return valueOps.get(key);
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public boolean removeValue(String key) {
        key = String.format("%s%s", KEY_PREFIX_VALUE, key);
        return remove(key);
    }

    @Override
    public boolean removeSet(String key) {
        return remove(key);
    }

    @Override
    public boolean removeList(String key) {
        return remove(key);
    }

    @Override
    public boolean cacheSet(String key, String value, long time) {
        try {
            SetOperations<String, String> valueOps = redisTemplate.opsForSet();
            valueOps.add(key, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean cacheSet(String key, String value) {
        return cacheSet(key, value, -1);
    }

    @Override
    public boolean cacheSet(String key, Set<String> value, long time) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            setOps.add(key, value.toArray(new String[value.size()]));
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean cacheSet(String k, Set<String> v) {
        return cacheSet(k, v, -1);
    }

    @Override
    public Set<String> getSet(String k) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            return setOps.members(k);
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public boolean cacheList(String k, String v, long time) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPush(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean cacheList(String k, String v) {
        return cacheList(k, v, -1);
    }

    @Override
    public boolean cacheList(String k, List<String> v, long time) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPushAll(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean cacheList(String k, List<String> v) {
        return cacheList(k, v, -1);
    }

    @Override
    public List<String> getList(String k, long start, long end) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.range( k, start, end);
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public long getListSize(String key) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            return listOps.size(key);
        } catch (Throwable t) {
            return 0;
        }
    }

    @Override
    public boolean removeOneOfList(String k) {
        try {
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            listOps.rightPop(k);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * 移除缓存
     *
     * @param key key
     * @return boolean
     */
    private boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
