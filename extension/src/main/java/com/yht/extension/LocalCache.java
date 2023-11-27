package com.yht.extension;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * support class for locale cache in one request
 */
@Slf4j
public class LocalCache {

    /**
     * identity key name
     */
    private static final String KEY_IDENTITY = "identity";

    /**
     * user Id
     */
    private static final String KEY_USER_ID = "userId";

    /**
     * bean support
     */
    private static final String KEY_BEAN_SUPPORT = "beanSupport";

    /**
     * cache params in one request
     */
    private static ThreadLocal<Map<String, Object>> threadLocalCache = new ThreadLocal<Map<String, Object>>();

    /**
     * cache params in runtime
     */
    private static Map<String, Object> runtimeCache = new ConcurrentHashMap<String, Object>();

    /**
     * put data to thread locale cache
     *
     * @param key  data key
     * @param data data
     */
    public static void putThreadLocaleCache(String key, Object data) {
        Map<String, Object> map = threadLocalCache.get();
        if (map == null) {
            threadLocalCache.set(new HashMap<>());
        }
        threadLocalCache.get().put(key, data);
    }

    /**
     * get data from thread locale cache
     *
     * @param key data key
     * @return data
     */
    public static <T> T getThreadLocaleCache(String key) {
        Map<String, Object> map = threadLocalCache.get();
        if (map == null) {
            return null;
        } else {
            return (T)map.get(key);
        }
    }

    /**
     * put data to locale cache
     *
     * @param key  data key
     * @param data data
     */
    public static void putCache(String key, Object data) {
        runtimeCache.put(key, data);
    }

    /**
     * get data from locale cache
     *
     * @param key data key
     * @return data
     */
    public static <T> T getCache(String key) {
        return (T)runtimeCache.get(key);
    }

    /**
     * get data from locale cache
     *
     * @param key   data key
     * @param clazz data type class
     * @return data
     */
    public static <T> T getThreadLocaleCache(String key, Class<T> clazz) {
        Map<String, Object> map = threadLocalCache.get();
        if (map == null) {
            return null;
        } else {
            return (T)map.get(key);
        }
    }

    /**
     * get data from locale cache
     *
     * @param key   data key
     * @param clazz data type class
     * @return data
     */
    public static <T> T getCache(String key, Class<T> clazz) {
        return (T)runtimeCache.get(key);
    }

    /**
     * clear the thread local cache
     */
    public static void clearThreadLocalCache(){
        Map<String, Object> threadLocalMap =  threadLocalCache.get();
        if (threadLocalMap != null){
            threadLocalMap.clear();
        }
    }

    /**
     * put identity to cache
     *
     * @param identity identity
     */
    public static void putIdentity(IdentityDO identity) {
        putThreadLocaleCache(KEY_IDENTITY, identity);
    }

    /**
     * @return cached identity
     */
    public static IdentityDO getIdentity() {
        IdentityDO identityDO = getThreadLocaleCache(KEY_IDENTITY, IdentityDO.class);
        if (identityDO == null){
            return BusinessIdentityUtil.getDefaultIdentity();
        }
        return identityDO;
    }

    /**
     * put userId to cache; just use for framework, not suggest application to use
     *
     * @param userId identity
     */
    public static void putUserId(Long userId) {
        putThreadLocaleCache(KEY_USER_ID, userId);
    }

    /**
     * just use for framework, not suggest application to use
     *
     * @return cached userId
     */
    public static Long getUserId() {
        return getThreadLocaleCache(KEY_USER_ID, Long.class);
    }

    /**
     * @return beanSupport
     */
    public static BeanSupport getBeanSupport() {
        return getCache(KEY_BEAN_SUPPORT, BeanSupport.class);
    }

    /**
     * set beanSupport
     */
    public static void setBeanSupport(BeanSupport beanSupport) {
        runtimeCache.put(KEY_BEAN_SUPPORT, beanSupport);
    }
}
