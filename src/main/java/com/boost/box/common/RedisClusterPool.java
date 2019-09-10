package com.boost.box.common;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class RedisClusterPool {
    private static JedisCluster jedisCluster;
    static {
        // 节点集合
        Set<HostAndPort> nodeSet = new HashSet<>();
        nodeSet.add(new HostAndPort("192.168.40.21", 6390));
        nodeSet.add(new HostAndPort("192.168.40.20", 6395));
        nodeSet.add(new HostAndPort("192.168.40.21", 6392));
        nodeSet.add(new HostAndPort("192.168.40.21", 6391));
        nodeSet.add(new HostAndPort("192.168.40.21", 6393));
        nodeSet.add(new HostAndPort("192.168.40.20", 6394));

        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        jedisCluster = new JedisCluster(nodeSet,jedisPoolConfig);
        System.out.println("redis池初始化成功...");
    }

    public static JedisCluster getJedisCluster(){
        return jedisCluster;
    }
}