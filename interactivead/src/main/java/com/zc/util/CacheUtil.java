package com.zc.util;

import org.solar.cache.Cache;

public class CacheUtil{
    public static Cache getDefaultCache() {
        return Cache.getDefaultCache();
    }

    public static Cache getCache(Object key) {
        return Cache.getCache(key);
    }
}
