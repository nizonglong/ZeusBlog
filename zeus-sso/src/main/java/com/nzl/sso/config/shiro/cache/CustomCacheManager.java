package com.nzl.sso.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author: nizonglong
 * @date: 2020/3/16 13:55
 * @desc: 重写Shiro缓存管理器
 * @version: 0.1
 **/
public class CustomCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<K, V>();
    }
}
