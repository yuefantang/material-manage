package com.dongyu.company.dict.cachemanage;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态数据缓存管理类
 *
 * @author TYF
 * @date 2019/5/14
 * @since 1.0.0
 */
public class CacheManager {

    private static Map<String, Object> caches;

    private CacheManager() {
    }

    static {
        caches = new HashMap<>();
    }

    //用于保存缓存
    public static void addCache(String key, Object value) {
        caches.put(key, value);
    }//用于得到缓存

    public static Object getCache(String key) {
        return caches.get(key);
    }

    //用于清除缓存信息
    public static void clearCache() {
        caches.clear();
    }

    //用于清除指定的缓存信息
    public static void removeCache(String key) {
        caches.remove(key);
    }

}
