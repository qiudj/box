package com.boost.box.redis.common;

import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * 基于Redis实现的分布式锁
 * @author qdj
 * @date 2019年11月05日 18:37:52
 */
public class DistributedLock {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long UNLOCK_SUCCESS = 1L;
    private static final JedisCluster JEDIS = RedisClusterPool.getJedisCluster();

    /**
    * 分布式锁的过期时间要考虑实际的业务场景
    * 也可以在业务处理的时候，for限制次数，或者设置重试时间
    */
    public static boolean tryLock(String lock, String requestId, int expireTime){
        String result = JEDIS.set(lock, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 延长锁的过期时间
     */
    public static void prolongLockTime(String lock, int expireTime){
        JEDIS.expire(lock, expireTime);
    }

    public static boolean unlock(String lock, String requestId){
        /*
         * 首先获取锁对应的value值，检查是否与requestId相等，
         * 如果相等则删除锁（解锁）。这样来说先get比较再释放锁并不是原子的，
         * 此处使用Lua语言来实现的原因是保证上述操作是原子性的。
         */
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = JEDIS.eval(script,
                Collections.singletonList(lock),
                Collections.singletonList(requestId));
        return UNLOCK_SUCCESS.equals(result);
    }
}